package org.webppo.clubcommunity_backend.controller.club;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.dto.club.ClubUpdateRequest;
import org.webppo.clubcommunity_backend.dto.club.IsMasterDto;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.club.ClubService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/{clubId}/is-master")
    public ResponseEntity<ResponseBody<IsMasterDto>> isMaster(@PathVariable Long clubId) {
        Boolean result = clubService.isMaster(PrincipalHandler.extractId(), clubId);
        IsMasterDto response = new IsMasterDto(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(response));
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ResponseBody<ClubDto>> findById(@PathVariable Long clubId) {
        ClubDto response = clubService.findById(clubId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(response));

    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<ClubDto>>> findAll() {
        List<ClubDto> response = clubService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(response));
    }

    @PatchMapping("/{clubId}/update")
    public ResponseEntity<ResponseBody<Void>> updateClub(@PathVariable Long clubId,
                                                         @Valid @ModelAttribute ClubUpdateRequest request) {
        clubService.updateClub(request, PrincipalHandler.extractId(), clubId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createSuccessResponse());
    }

    // 내가 속하는 모든 동아리 목록
    @GetMapping("/me")
    public ResponseEntity<ResponseBody<List<ClubDto>>> findMemberId() {
        List<ClubDto> responses = clubService.findMemberId(PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(responses));
    }

    // 동아리 안의 모든 회원 정보 - master
    @GetMapping("/{clubId}/members")
    public ResponseEntity<ResponseBody<List<MemberDto>>> findClubMembers(@PathVariable Long clubId) {
        List<MemberDto> responses = clubService.findClubMembers(clubId, PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(responses));
    }

    @DeleteMapping("{clubId}/members/{memberId}")
    public ResponseEntity<ResponseBody<Void>> deleteClubMember(@PathVariable Long clubId, @PathVariable Long memberId) {
        clubService.deleteClubMember(clubId, memberId, PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }

}
