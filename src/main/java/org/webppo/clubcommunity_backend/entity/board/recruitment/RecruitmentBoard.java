package org.webppo.clubcommunity_backend.entity.board.recruitment;

import jakarta.persistence.*;
import org.webppo.clubcommunity_backend.entity.board.Board;

@Entity
@DiscriminatorValue("recruitment")
public class RecruitmentBoard extends Board {
    @Column(nullable = false)
    private String content;
}
