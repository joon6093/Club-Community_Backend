package org.webppo.clubcommunity_backend.dto.board.video;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.entity.board.video.Video;

import java.util.List;

@Getter
@AllArgsConstructor
public class VideoUpdatedResult {
    private List<MultipartFile> addedVideoFiles;
    private List<Video> addedVideos;
    private List<Video> deletedVideos;
}
