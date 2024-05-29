package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.common.EntityDate;
import org.webppo.clubcommunity_backend.entity.member.Member;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "board_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Board extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    protected String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    protected Member member;

    public Board(String title, Club club, Member member) {
        this.title = title;
        this.club = club;
        this.member = member;
    }
}
