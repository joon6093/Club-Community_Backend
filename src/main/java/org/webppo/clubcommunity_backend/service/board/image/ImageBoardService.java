package org.webppo.clubcommunity_backend.service.board.image;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardDto;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageUpdatedResult;
import org.webppo.clubcommunity_backend.entity.board.image.Image;
import org.webppo.clubcommunity_backend.entity.board.image.ImageBoard;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.board.image.ImageBoardRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.board.BoardNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;

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
    private final ImageService imageService;

    @Transactional
    public ImageBoardDto create(Long memberId, ImageBoardCreateRequest req) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        ImageBoard imageBoard = ImageBoard.builder()
                .title(req.getTitle())
                .member(member)
                .club(null) // TODO. Club 찾아서 반환
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
        IntStream.range(0, images.size()).forEach(i -> imageService.upload(fileImages.get(i), images.get(i).getUniqueName()));
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

    public List<ImageBoardDto> readAll(Long clubId) {  // TODO. 추후 구현
        return null;
    }

    @Transactional
    public void delete(Long id) {
        ImageBoard imageBoard = imageBoardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        deleteImages(imageBoard.getImages());
        imageBoardRepository.delete(imageBoard);
    }

    private void deleteImages(List<Image> images) {
        images.forEach(image -> imageService.delete(image.getUniqueName()));
    }

    @Transactional
    public ImageBoardDto update(@Param("id") Long id, ImageBoardUpdateRequest req) {
        ImageBoard imageBoard = imageBoardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        ImageUpdatedResult result = imageBoard.update(req);
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
}
