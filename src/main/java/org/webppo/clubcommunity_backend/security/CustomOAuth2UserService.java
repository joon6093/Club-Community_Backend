package org.webppo.clubcommunity_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.Role;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.repository.member.RoleRepository;
import org.webppo.clubcommunity_backend.response.exception.member.RoleNotFoundException;
import org.webppo.clubcommunity_backend.security.response.OAuth2Response;
import org.webppo.clubcommunity_backend.security.response.OAuth2ResponseFactory;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = OAuth2ResponseFactory.getOAuth2Response(registrationId, oAuth2User.getAttributes());
        String name = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

        Member member = memberRepository.findByNameWithRoles(name)
                .map(existingMember -> {
                    existingMember.updateInfo(oAuth2Response.getName(), oAuth2Response.getProfileImage());
                    return existingMember;
                })
                .orElseGet(() -> {
                    Role pendingRole = roleRepository.findByRoleType(RoleType.ROLE_PENDING).orElseThrow(RoleNotFoundException::new);
                    return memberRepository.save(
                            Member.builder()
                                    .name(name)
                                    .username(oAuth2Response.getName())
                                    .profileImage(oAuth2Response.getProfileImage())
                                    .role(pendingRole)
                                    .build()
                    );
                });

        return new CustomOAuth2UserDetails(member.getId(), name, null, Collections.singleton(new SimpleGrantedAuthority(member.getRole().getRoleType().name())));
    }
}
