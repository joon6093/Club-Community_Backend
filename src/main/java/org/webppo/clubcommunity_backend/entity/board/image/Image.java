package org.webppo.clubcommunity_backend.entity.board.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.board.File;

@Entity
@Getter
@DiscriminatorValue("image")
@NoArgsConstructor
public class Image extends File {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private ImageBoard imageBoard;

    private final static String[] supportedExtension = {"jpg", "jpeg", "gif", "bmp", "png"};

    static {
        supportedExtensions = supportedExtension;
    }

    public Image(String originName) {
        super(originName);
    }

    public void setImageBoard(ImageBoard imageBoard) {
        if (this.imageBoard == null) {
            this.imageBoard = imageBoard;
        }
    }
}
