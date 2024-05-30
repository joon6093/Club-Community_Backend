package org.webppo.clubcommunity_backend.controller.club.clubJoinForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ProgressChangeRequest;
import org.webppo.clubcommunity_backend.dto.club.clubJoinForm.ClubJoinFormCreateRequest;
import org.webppo.clubcommunity_backend.dto.club.clubJoinForm.ClubJoinFormResponse;
import org.webppo.clubcommunity_backend.response.FailedResponseBody;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.club.clubJoinForm.ClubJoinFormService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/club-join-forms")
public class ClubJoinFormController {

    private final ClubJoinFormService clubJoinFormService;

    @PostMapping("/{clubId}/upload")
    public ResponseEntity<ResponseBody<Void>> createClubJoinForm(@Valid @ModelAttribute ClubJoinFormCreateRequest request,
                                                                 @PathVariable Long clubId) {
        clubJoinFormService.createClubJoinForm(request, clubId, PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createSuccessResponse());
    }

    @GetMapping("/{clubId}/club")
    public ResponseEntity<ResponseBody<List<ClubJoinFormResponse>>> getClubJoinForms(@PathVariable Long clubId) {
        List<ClubJoinFormResponse> responses = clubJoinFormService.getClubJoinForms(clubId, PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(responses));
    }

    @PatchMapping("/{joinFormId}/progress")
    public ResponseEntity<ResponseBody<Void>> updateJoinFormProgress(
            @PathVariable Long joinFormId,
            @RequestBody @Valid ProgressChangeRequest request) {
        clubJoinFormService.updateJoinFormProgress(joinFormId, request.getProgress(), PrincipalHandler.extractId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }

}
