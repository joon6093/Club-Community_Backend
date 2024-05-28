package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("video")
public class VideoBoard extends Board{
    @Column(nullable = false)
    private String videoUrl;
}
