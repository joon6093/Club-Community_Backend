package org.webppo.clubcommunity_backend.dto.board.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardDto {
    private Long id;
    private String title;
    private List<ImageDto> images;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDto {
        private Long id;
        private String uniqueName;
        private String originName;
    }
}
