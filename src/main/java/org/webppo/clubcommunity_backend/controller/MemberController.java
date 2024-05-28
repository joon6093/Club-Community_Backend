package org.webppo.clubcommunity_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.dto.member.MemberSignupRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberUpdateRequest;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import org.webppo.clubcommunity_backend.service.member.MemberService;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseBody<MemberDto>> signup(@Valid @RequestBody MemberSignupRequest memberSignupRequest) {
        MemberDto memberDto = memberService.register(memberSignupRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(memberDto));
    }

    @PatchMapping("/pending")
    public ResponseEntity<ResponseBody<MemberDto>> update(@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        MemberDto memberDto = memberService.update(PrincipalHandler.extractId(), memberUpdateRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(memberDto));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseBody<MemberDto>> readMe() {
        MemberDto memberDto = memberService.read(PrincipalHandler.extractId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse(memberDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResponseBody<Void>> deleteMe(HttpServletRequest request, HttpServletResponse response) {
        memberService.delete(PrincipalHandler.extractId(), request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createSuccessResponse());
    }
}
