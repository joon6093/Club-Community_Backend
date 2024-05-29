package org.webppo.clubcommunity_backend.entity.board.video;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.board.video.VideoUpdatedResult;
import org.webppo.clubcommunity_backend.entity.board.Board;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@Entity
@Getter
@DiscriminatorValue("video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoBoard extends Board {

    @OneToMany(mappedBy = "videoBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Video> videos = new ArrayList<>();

    @Builder
    public VideoBoard(String title, Club club, Member member) {
        super(title, club, member);
    }

    public void addVideos(List<Video> videos) {
        videos.forEach(video -> {
            this.videos.add(video);
            video.setVideoBoard(this);
        });
    }

    public void removeVideos(List<Video> videoList) {
        List<Video> videosToRemove = new ArrayList<>(videoList);
        for (Video video : videosToRemove) {
            this.videos.remove(video);
            video.setVideoBoard(null);
        }
    }

    public VideoUpdatedResult update(Member member, VideoBoardUpdateRequest req) {
        this.title = req.getTitle();
        this.member = member;
        VideoUpdatedResult result = findVideoUpdatedResult(req.getAddedVideos(), req.getDeletedVideos());
        addVideos(result.getAddedVideos());
        removeVideos(result.getDeletedVideos());
        return result;
    }

    private VideoUpdatedResult findVideoUpdatedResult(List<MultipartFile> addedVideoFiles, List<Long> deletedVideoIds) {
        List<Video> addedVideos = convertVideoFilesToVideos(addedVideoFiles);
        List<Video> deletedVideos = convertVideoIdsToVideos(deletedVideoIds);
        return new VideoUpdatedResult(addedVideoFiles, addedVideos, deletedVideos);
    }

    private List<Video> convertVideoFilesToVideos(List<MultipartFile> videoFiles) {
        return videoFiles.stream().map(videoFile -> new Video(videoFile.getOriginalFilename())).collect(toList());
    }

    private List<Video> convertVideoIdsToVideos(List<Long> videoIds) {
        return videoIds.stream()
                .map(this::convertVideoIdToVideo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    private Optional<Video> convertVideoIdToVideo(Long id) {
        return this.videos.stream().filter(i -> i.getId().equals(id)).findAny();
    }
}
