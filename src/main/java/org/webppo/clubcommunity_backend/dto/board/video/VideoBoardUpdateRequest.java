package org.webppo.clubcommunity_backend.dto.board.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.controller.board.vaild.ValidVideoFileList;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoBoardUpdateRequest {

    @ValidVideoFileList
    private List<MultipartFile> addedVideos = new ArrayList<>();
    private List<Long> deletedVideos = new ArrayList<>();
}
