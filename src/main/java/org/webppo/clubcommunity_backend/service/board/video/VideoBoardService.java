package org.webppo.clubcommunity_backend.service.board.video;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardDto;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.board.video.VideoUpdatedResult;
import org.webppo.clubcommunity_backend.entity.board.video.Video;
import org.webppo.clubcommunity_backend.entity.board.video.VideoBoard;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.board.video.VideoBoardRepository;
import org.webppo.clubcommunity_backend.repository.club.ClubRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.board.BoardNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.ClubNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.NotClubMasterException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.FileService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoBoardService {

    private final VideoBoardRepository videoBoardRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final FileService fileService;

    @Transactional
    public VideoBoardDto create(Long memberId, VideoBoardCreateRequest req) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Club club = clubRepository.findById(req.getClubId()).orElseThrow(ClubNotFoundException::new);
        checkClubMaster(member, club);

        VideoBoard videoBoard = VideoBoard.builder()
                .title(req.getTitle())
                .member(member)
                .club(club)
                .build();

        videoBoard.addVideos(req.getVideos().stream()
                .map(i -> new Video(i.getOriginalFilename()))
                .collect(toList()));

        videoBoardRepository.save(videoBoard);
        uploadVideos(videoBoard.getVideos(), req.getVideos());

        return new VideoBoardDto(videoBoard.getId(), videoBoard.getTitle(), videoBoard.getVideos().stream()
                .map(video -> new VideoBoardDto.VideoDto(video.getId(), video.getUniqueName(), video.getOriginName()))
                .collect(Collectors.toList()));
    }

    private void uploadVideos(List<Video> videos, List<MultipartFile> fileVideos) {
        IntStream.range(0, videos.size()).forEach(i -> fileService.upload(fileVideos.get(i), videos.get(i).getUniqueName(), "video"));
    }

    @Transactional
    public VideoBoardDto update(@Param("id") Long id, VideoBoardUpdateRequest req) {
        VideoBoard videoBoard = videoBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, videoBoard.getClub());

        VideoUpdatedResult result = videoBoard.update(req);
        uploadVideos(result.getAddedVideos(), result.getAddedVideoFiles());
        deleteVideos(result.getDeletedVideos());
        videoBoardRepository.save(videoBoard);

        return new VideoBoardDto(
                videoBoard.getId(),
                videoBoard.getTitle(),
                videoBoard.getVideos().stream()
                        .map(video -> new VideoBoardDto.VideoDto(video.getId(), video.getUniqueName(), video.getOriginName()))
                        .collect(Collectors.toList())
        );
    }

    public VideoBoardDto read(Long id) {
        VideoBoard videoBoard = videoBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        return new VideoBoardDto(
                videoBoard.getId(),
                videoBoard.getTitle(),
                videoBoard.getVideos().stream()
                        .map(video -> new VideoBoardDto.VideoDto(video.getId(), video.getUniqueName(), video.getOriginName()))
                        .collect(Collectors.toList())
        );
    }

    public List<VideoBoardDto> readAll(Long clubId) {
        List<VideoBoard> videoBoards = videoBoardRepository.findAllByClubId(clubId);

        return videoBoards.stream()
                .map(videoBoard -> new VideoBoardDto(
                        videoBoard.getId(),
                        videoBoard.getTitle(),
                        videoBoard.getVideos().stream()
                                .map(video -> new VideoBoardDto.VideoDto(video.getId(), video.getUniqueName(), video.getOriginName()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        VideoBoard videoBoard = videoBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, videoBoard.getClub());

        deleteVideos(videoBoard.getVideos());
        videoBoardRepository.delete(videoBoard);
    }

    private void deleteVideos(List<Video> videos) {
        videos.forEach(video -> fileService.delete(video.getUniqueName(), "video"));
    }

    private void checkClubMaster(Member member, Club club) {
        if (!club.getClubMaster().equals(member)) {
            throw new NotClubMasterException();
        }
    }
}
