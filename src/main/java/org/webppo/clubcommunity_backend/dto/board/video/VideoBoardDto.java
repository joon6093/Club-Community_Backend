package org.webppo.clubcommunity_backend.dto.board.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoBoardDto {
    private Long id;
    private String title;
    private List<VideoDto> images;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoDto {
        private Long id;
        private String uniqueName;
        private String originName;
    }
}
