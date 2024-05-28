package org.webppo.clubcommunity_backend.controller.board.video;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardDto;
import org.webppo.clubcommunity_backend.dto.board.video.VideoBoardUpdateRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.video.VideoBoardService;
import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/video")
public class VideoBoardController {

    private final VideoBoardService videoBoardService;

    @PostMapping
    public ResponseEntity<ResponseBody<VideoBoardDto>> create(@Valid @ModelAttribute VideoBoardCreateRequest request) {
        VideoBoardDto videoBoardDto = videoBoardService.create(PrincipalHandler.extractId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(videoBoardDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<VideoBoardDto>> update(@PathVariable Long id, @Valid @ModelAttribute VideoBoardUpdateRequest request) {
        VideoBoardDto videoBoardDto = videoBoardService.update(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(videoBoardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<VideoBoardDto>> read(@PathVariable Long id) {
        VideoBoardDto videoBoardDto = videoBoardService.read(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(videoBoardDto));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<ResponseBody<List<VideoBoardDto>>> readAll(@PathVariable Long clubId) {
        List<VideoBoardDto> videoBoardDtos = videoBoardService.readAll(clubId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(videoBoardDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        videoBoardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
