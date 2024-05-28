package org.webppo.clubcommunity_backend.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.dto.member.MemberSignupRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberUpdateRequest;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.Role;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.repository.member.RoleRepository;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.member.RoleNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.member.UpdateAuthenticationFailureException;
import org.webppo.clubcommunity_backend.security.CustomOAuth2UserDetails;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto register(MemberSignupRequest request) {
        Role role = roleRepository.findByRoleType(RoleType.ROLE_USER)
                .orElseThrow(RoleNotFoundException::new);

        Member member = memberRepository.save(
                Member.builder()
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .profileImage(request.getProfileImage())
                        .birthDate(request.getBirthDate())
                        .gender(request.getGender())
                        .department(request.getDepartment())
                        .studentId(request.getStudentId())
                        .phoneNumber(request.getPhoneNumber())
                        .email(request.getEmail())
                        .registrationType(request.getRegistrationType())
                        .role(role)
                        .build());

        return new MemberDto(
                member.getId(),
                member.getName(),
                member.getUsername(),
                member.getProfileImage(),
                member.getRole().getRoleType(),
                member.getBirthDate(),
                member.getGender(),
                member.getDepartment(),
                member.getStudentId(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getRegistrationType(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    public MemberDto read(@Param("id")Long id) {
        return memberRepository.findDtoById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public MemberDto update(Long id, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER).orElseThrow(RoleNotFoundException::new);
        member.updateAdditionalInfo(memberUpdateRequest.getBirthDate(), memberUpdateRequest.getGender(),
                memberUpdateRequest.getDepartment(), memberUpdateRequest.getStudentId(), memberUpdateRequest.getPhoneNumber(),
                memberUpdateRequest.getEmail(), memberUpdateRequest.getRegistrationType(), userRole);
        updateAuthentication(member);
        return new MemberDto(
                member.getId(),
                member.getName(),
                member.getUsername(),
                member.getProfileImage(),
                member.getRole().getRoleType(),
                member.getBirthDate(),
                member.getGender(),
                member.getDepartment(),
                member.getStudentId(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getRegistrationType(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    public void updateAuthentication(Member member) {
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(this::isOAuth2AuthenticationToken)
                .map(OAuth2AuthenticationToken.class::cast)
                .map(oauth2AuthenticationToken -> getOAuth2AuthenticationToken(oauth2AuthenticationToken, member))
                .ifPresentOrElse(
                        SecurityContextHolder.getContext()::setAuthentication,
                        () -> {
                            throw new UpdateAuthenticationFailureException();
                        }
                );
    }

    private boolean isOAuth2AuthenticationToken(Authentication authentication) {
        return authentication instanceof OAuth2AuthenticationToken;
    }

    private OAuth2AuthenticationToken getOAuth2AuthenticationToken(OAuth2AuthenticationToken oauth2AuthenticationToken, Member member) {
        return new OAuth2AuthenticationToken(
                getPrincipal(member),
                getAuthority(member),
                oauth2AuthenticationToken.getAuthorizedClientRegistrationId()
        );
    }

    private CustomOAuth2UserDetails getPrincipal(Member member) {
        return new CustomOAuth2UserDetails(
                member.getId(),
                member.getName(),
                member.getPassword(),
                getAuthority(member)
        );
    }

    private Set<GrantedAuthority> getAuthority(Member member) {
        RoleType roleType = member.getRole().getRoleType();
        return Collections.singleton(new SimpleGrantedAuthority(roleType.name()));
    }
}
