package org.webppo.clubcommunity_backend.entity.club.clubForm;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.common.EntityDate;
import org.webppo.clubcommunity_backend.entity.member.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClubForm extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_form_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String clubType; // 중앙 / 학과
    @Column(nullable = false)
    private String clubName;
    @Column(nullable = false)
    private String applicantDepartment;
    @Column(nullable = false)
    private String advisorName;
    @Column(nullable = false)
    private String advisorMajor;
    @Column(nullable = false)
    private String advisorContact;
    @Enumerated(EnumType.STRING) // status (검토/승인/거절)
    private ProgressType progress;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public ClubForm(String clubType, String clubName, String applicantDepartment, String advisorName, String advisorMajor, String advisorContact, ProgressType progress, Member member) {
        this.clubType = clubType;
        this.clubName = clubName;
        this.applicantDepartment = applicantDepartment;
        this.advisorName = advisorName;
        this.advisorMajor = advisorMajor;
        this.advisorContact = advisorContact;
        this.progress = progress;
        this.member = member;
    }
}
