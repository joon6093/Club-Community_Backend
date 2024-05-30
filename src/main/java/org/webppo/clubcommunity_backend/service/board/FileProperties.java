package org.webppo.clubcommunity_backend.service.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "upload")
public class FileProperties {
    private Image image;
    private Video video;
    private File file;

    @Getter
    @Setter
    public static class Image {
        private String location;
    }

    @Getter
    @Setter
    public static class Video {
        private String location;
    }

    @Getter
    @Setter
    public static class File {
        private String location;
    }
}
