package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("notice")
public class NoticeBoard extends Board{
    @Column(nullable = false)
    private String content;


}
