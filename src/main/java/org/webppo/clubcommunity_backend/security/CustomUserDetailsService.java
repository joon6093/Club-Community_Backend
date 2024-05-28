package org.webppo.clubcommunity_backend.security;

import lombok.RequiredArgsConstructor;
import org.changppo.account.entity.member.Member;
import org.changppo.account.response.exception.member.MemberNotFoundException;
import org.changppo.account.service.domain.member.MemberDomainService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDomainService memberDomainService;

    @Override
    @Transactional(readOnly = true)
    public CustomOAuth2UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDomainService.getOptionalMemberByNameWithRoles(username).orElseThrow(MemberNotFoundException::new);

        return new CustomOAuth2UserDetails(
                member.getId(),
                member.getName(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getRoleType().name()))
        );
    }
}
