package org.webppo.clubcommunity_backend.dto.board.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.controller.board.vaild.ValidImageFileList;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageBoardUpdateRequest {

    @ValidImageFileList
    private List<MultipartFile> addedImages = new ArrayList<>();

    private List<Long> deletedImages = new ArrayList<>();
}
