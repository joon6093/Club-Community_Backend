package org.webppo.clubcommunity_backend.dto.board.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.entity.board.image.Image;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUpdatedResult {
    private List<MultipartFile> addedImageFiles;
    private List<Image> addedImages;
    private List<Image> deletedImages;
}
