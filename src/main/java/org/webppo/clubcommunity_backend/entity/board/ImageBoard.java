package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("image")
public class ImageBoard extends Board{
    @Column(nullable = false)
    private String imageUrl;
}
