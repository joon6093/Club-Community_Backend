package org.webppo.clubcommunity_backend.controller.board.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardDto;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardUpdateRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.notice.NoticeBoardService;
import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/notice")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @PostMapping
    public ResponseEntity<ResponseBody<NoticeBoardDto>> create(@Valid @RequestBody NoticeBoardCreateRequest request) {
        NoticeBoardDto noticeBoardDto = noticeBoardService.create(PrincipalHandler.extractId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(noticeBoardDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<NoticeBoardDto>> update(@PathVariable Long id, @Valid @RequestBody NoticeBoardUpdateRequest request) {
        NoticeBoardDto noticeBoardDto = noticeBoardService.update(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(noticeBoardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<NoticeBoardDto>> read(@PathVariable Long id) {
        NoticeBoardDto noticeBoardDto = noticeBoardService.read(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(noticeBoardDto));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<ResponseBody<List<NoticeBoardDto>>> readAll(@PathVariable Long clubId) {
        List<NoticeBoardDto> noticeBoardDtos = noticeBoardService.readAll(clubId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(noticeBoardDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        noticeBoardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
