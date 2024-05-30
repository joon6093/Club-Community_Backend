package org.webppo.clubcommunity_backend.service.club.clubJoinForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.club.clubJoinForm.ClubJoinFormCreateRequest;
import org.webppo.clubcommunity_backend.dto.club.clubJoinForm.ClubJoinFormResponse;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.club.clubJoinForm.ClubJoinForm;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.club.ClubRepository;
import org.webppo.clubcommunity_backend.repository.club.clubJoinForm.ClubJoinFormRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.club.ClubNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.clubForm.RejectReasonNullException;
import org.webppo.clubcommunity_backend.response.exception.club.clubJoinForm.ClubJoinFormNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.service.board.LocalFileService;

import java.util.List;
import java.util.UUID;

import static org.webppo.clubcommunity_backend.service.club.ClubService.isMasterCheck;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ClubJoinFormService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubJoinFormRepository clubJoinFormRepository;
    private final LocalFileService localFileService;

    @Transactional
    public void createClubJoinForm(ClubJoinFormCreateRequest request, Long clubId, Long memberId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        MultipartFile file = request.getFile();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        localFileService.upload(file, fileName, "file");

        ClubJoinForm clubJoinForm = ClubJoinForm.builder()
                .club(club)
                .member(member)
                .filename(fileName)
                .progress(ProgressType.REVIEW)
                .build();

        clubJoinFormRepository.save(clubJoinForm);
    }

    public List<ClubJoinFormResponse> getClubJoinForms(Long clubId, Long memberId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        isMasterCheck(memberId, club);
        return clubJoinFormRepository.findByClub(club).stream()
                .map(ClubJoinFormResponse::new)
                .toList();
    }

    @Transactional
    public void updateJoinFormProgress(Long joinFormId, ProgressType progress, Long memberId) {
        ClubJoinForm clubJoinForm = clubJoinFormRepository.findById(joinFormId).orElseThrow(ClubJoinFormNotFoundException::new);
        isMasterCheck(memberId, clubJoinForm.getClub());
        if(progress == ProgressType.APPROVAL){
            clubJoinForm.setProgress(progress);
            clubJoinForm.getClub().addMember(clubJoinForm.getMember());
        } else if(progress == ProgressType.REJECT) {
            // 동아리 가입 신청을 거부했다면 아무일도 안함.
            return;
        } else {
            throw new IllegalArgumentException("ProgressType is not valid");
        }

    }
}
