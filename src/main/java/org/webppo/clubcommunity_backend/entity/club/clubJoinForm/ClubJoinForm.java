package org.webppo.clubcommunity_backend.entity.club.clubJoinForm;

import jakarta.persistence.*;
import lombok.*;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.common.EntityDate;
import org.webppo.clubcommunity_backend.entity.member.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClubJoinForm extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_join_form_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Setter
    @Enumerated(EnumType.STRING) // status (검토/승인/거절)
    private ProgressType progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public ClubJoinForm(String filename, ProgressType progress, Club club, Member member) {
        this.filename = filename;
        this.progress = progress;
        this.club = club;
        this.member = member;
    }
}
