package org.webppo.clubcommunity_backend.entity.board.notice;

import jakarta.persistence.*;
import org.webppo.clubcommunity_backend.entity.board.Board;

@Entity
@DiscriminatorValue("notice")
public class NoticeBoard extends Board {
    @Column(nullable = false)
    private String content;
}
