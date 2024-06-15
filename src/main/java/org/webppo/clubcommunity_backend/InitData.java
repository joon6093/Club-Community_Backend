package org.webppo.clubcommunity_backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile({"local", "dev"})
@Slf4j
public class InitData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        initAdmin();
    }

    private void initAdmin() {
        Member member = Member.builder()
                .name("admin")
                .password(passwordEncoder.encode("dnpqQH1234"))
                .username("admin")
                .profileImage("https://cdn-icons-png.flaticon.com/512/4919/4919646.png")
                .birthDate(LocalDateTime.of(1990, 1, 1, 0, 0))
                .gender("male")
                .department("Computer Science")
                .studentId("20210001")
                .phoneNumber("123-456-7890")
                .email("johndoe@example.com")
                .role(RoleType.ROLE_ADMIN)
                .build();
        memberRepository.save(member);
    }
}
