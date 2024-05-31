package org.webppo.clubcommunity_backend.controller.club.clubForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ClubFormDto;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ProgressChangeRequest;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ClubFormRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.club.clubForm.ClubFormService;

import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubForms")
public class ClubFormController {

    private final ClubFormService clubFormService;

    @PostMapping("/register")
    public ResponseEntity<ResponseBody<Void>> register(@Valid @RequestBody ClubFormRequest request) {
        clubFormService.register(request, PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createSuccessResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseBody<List<ClubFormDto>>> findAllById() {
        List<ClubFormDto> responses = clubFormService.findAllById(PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(responses));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<ClubFormDto>>> findAll() {
        List<ClubFormDto> responses = clubFormService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(responses));
    }

    @PatchMapping("/{clubFormId}/progress")
    public ResponseEntity<ResponseBody<Void>> clubFormChange(@PathVariable Long clubFormId,
                                                             @Valid @RequestBody ProgressChangeRequest request) {
        clubFormService.clubFormChange(clubFormId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
