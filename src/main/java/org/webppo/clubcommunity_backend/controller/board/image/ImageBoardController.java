package org.webppo.clubcommunity_backend.controller.board.image;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardDto;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.image.ImageBoardService;
import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/image")
public class ImageBoardController {

    private final ImageBoardService imageBoardService;

    @PostMapping
    public ResponseEntity<ResponseBody<ImageBoardDto>> create(@Valid @ModelAttribute ImageBoardCreateRequest request) {
        ImageBoardDto imageBoardDto = imageBoardService.create(PrincipalHandler.extractId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(imageBoardDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<ImageBoardDto>> update(@PathVariable Long id, @Valid @ModelAttribute ImageBoardUpdateRequest request) {
        ImageBoardDto imageBoardDto = imageBoardService.update(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(imageBoardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<ImageBoardDto>> read(@PathVariable Long id) {
        ImageBoardDto imageBoardDto = imageBoardService.read(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(imageBoardDto));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<ResponseBody<List<ImageBoardDto>>> readAll(@PathVariable Long clubId) {
        List<ImageBoardDto> imageBoardDtos = imageBoardService.readAll(clubId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(imageBoardDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        imageBoardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
