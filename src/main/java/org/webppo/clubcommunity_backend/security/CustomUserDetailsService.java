package org.webppo.clubcommunity_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomOAuth2UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);

        return new CustomOAuth2UserDetails(
                member.getId(),
                member.getName(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }
}
