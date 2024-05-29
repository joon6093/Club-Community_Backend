package org.webppo.clubcommunity_backend.controller.board.recruitment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardDto;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardUpdateRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.board.recruitment.RecruitmentBoardService;

import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/recruitment")
public class RecruitmentBoardController {

    private final RecruitmentBoardService recruitmentBoardService;

    @PostMapping
    public ResponseEntity<ResponseBody<RecruitmentBoardDto>> create(@Valid @RequestBody RecruitmentBoardCreateRequest request) {
        RecruitmentBoardDto recruitmentBoardDto = recruitmentBoardService.create(PrincipalHandler.extractId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(recruitmentBoardDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<RecruitmentBoardDto>> update(@PathVariable Long id, @Valid @RequestBody RecruitmentBoardUpdateRequest request) {
        RecruitmentBoardDto recruitmentBoardDto = recruitmentBoardService.update(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(recruitmentBoardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<RecruitmentBoardDto>> read(@PathVariable Long id) {
        RecruitmentBoardDto recruitmentBoardDto = recruitmentBoardService.read(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(recruitmentBoardDto));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<ResponseBody<List<RecruitmentBoardDto>>> readAll(@PathVariable Long clubId) {
        List<RecruitmentBoardDto> recruitmentBoardDtos = recruitmentBoardService.readAll(clubId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(recruitmentBoardDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        recruitmentBoardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
