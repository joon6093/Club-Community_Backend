package org.webppo.clubcommunity_backend.entity.board.recruitment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardUpdateRequest;
import org.webppo.clubcommunity_backend.entity.board.Board;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;

@Entity
@Getter
@DiscriminatorValue("recruitment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentBoard extends Board {

    @Column(nullable = false)
    private String content;

    @Builder
    public RecruitmentBoard(String title, String content, Club club, Member member) {
        super(title, club, member);
        this.content = content;
    }

    public void update(Member member, RecruitmentBoardUpdateRequest req) {
        this.member = member;
        if (req.getTitle() != null) {
            this.title = req.getTitle();
        }
        if (req.getContent() != null) {
            this.content = req.getContent();
        }
    }
}
