package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("recruitment")
public class RecruitmentBoard extends Board{
    @Column(nullable = false)
    private String content;
}
