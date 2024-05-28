package org.webppo.clubcommunity_backend.entity.board.video;

import jakarta.persistence.*;
import org.webppo.clubcommunity_backend.entity.board.Board;

@Entity
@DiscriminatorValue("video")
public class VideoBoard extends Board {
    @Column(nullable = false)
    private String videoUrl;
}
