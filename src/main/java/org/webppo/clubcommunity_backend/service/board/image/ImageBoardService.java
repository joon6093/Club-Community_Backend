package org.webppo.clubcommunity_backend.service.board.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardDto;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageUpdatedResult;
import org.webppo.clubcommunity_backend.entity.board.image.Image;
import org.webppo.clubcommunity_backend.entity.board.image.ImageBoard;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.board.image.ImageBoardRepository;
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
public class ImageBoardService {

    private final ImageBoardRepository imageBoardRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final FileService fileService;

    @Transactional
    public ImageBoardDto create(Long memberId, ImageBoardCreateRequest req) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Club club = clubRepository.findById(req.getClubId()).orElseThrow(ClubNotFoundException::new);
        checkClubMaster(member, club);

        ImageBoard imageBoard = ImageBoard.builder()
                .title(req.getTitle())
                .member(member)
                .club(club)
                .build();

        imageBoard.addImages(req.getImages().stream()
                .map(i -> new Image(i.getOriginalFilename()))
                .collect(toList()));

        imageBoardRepository.save(imageBoard);
        uploadImages(imageBoard.getImages(), req.getImages());

        return new ImageBoardDto(imageBoard.getId(), imageBoard.getTitle(), imageBoard.getImages().stream()
                                                                            .map(image -> new ImageBoardDto.ImageDto(image.getId(), image.getUniqueName(), image.getOriginName()))
                                                                            .collect(Collectors.toList()));
    }

    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName(), "image"));
    }

    @Transactional
    public ImageBoardDto update(Long id, ImageBoardUpdateRequest req) {
        ImageBoard imageBoard = imageBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, imageBoard.getClub());

        ImageUpdatedResult result = imageBoard.update(member, req);
        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());
        imageBoardRepository.save(imageBoard);

        return new ImageBoardDto(
                imageBoard.getId(),
                imageBoard.getTitle(),
                imageBoard.getImages().stream()
                        .map(image -> new ImageBoardDto.ImageDto(image.getId(), image.getUniqueName(), image.getOriginName()))
                        .collect(Collectors.toList())
        );
    }

    public ImageBoardDto read(Long id) {
        ImageBoard imageBoard = imageBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        return new ImageBoardDto(
                imageBoard.getId(),
                imageBoard.getTitle(),
                imageBoard.getImages().stream()
                        .map(image -> new ImageBoardDto.ImageDto(image.getId(), image.getUniqueName(), image.getOriginName()))
                        .collect(Collectors.toList())
        );
    }

    public List<ImageBoardDto> readAll(Long clubId) {
        List<ImageBoard> imageBoards = imageBoardRepository.findAllByClubId(clubId);

        return imageBoards.stream()
                .map(imageBoard -> new ImageBoardDto(
                        imageBoard.getId(),
                        imageBoard.getTitle(),
                        imageBoard.getImages().stream()
                                .map(image -> new ImageBoardDto.ImageDto(image.getId(), image.getUniqueName(), image.getOriginName()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        ImageBoard imageBoard = imageBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, imageBoard.getClub());

        deleteImages(imageBoard.getImages());
        imageBoardRepository.delete(imageBoard);
    }

    private void deleteImages(List<Image> images) {
        images.forEach(image -> fileService.delete(image.getUniqueName(), "image"));
    }

    private void checkClubMaster(Member member, Club club) {
        if (!club.getClubMaster().equals(member)) {
            throw new NotClubMasterException();
        }
    }
}
