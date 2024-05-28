package org.webppo.clubcommunity_backend.entity.board.video;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.response.exception.board.UnsupportedFileFormatException;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Column(nullable = false)
    private String uniqueName;

    @Column(nullable = false)
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private VideoBoard videoBoard;

    private final static String[] supportedExtension = {"mp4", "avi", "mov", "wmv", "flv"};

    public Video(String originName) {
        this.uniqueName = generateUniqueName(extractExtension(originName));
        this.originName = originName;
    }

    public void setVideoBoard(VideoBoard videoBoard) {
        if(this.videoBoard == null) {
            this.videoBoard = videoBoard;
        }
    }

    private String generateUniqueName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if(isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) {
            throw new UnsupportedFileFormatException(e);
        }
        throw new UnsupportedFileFormatException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }

}
