package org.webppo.clubcommunity_backend.service.club.clubForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ClubFormDto;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ClubFormProgressRequest;
import org.webppo.clubcommunity_backend.dto.club.clubForm.ClubFormRequest;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.club.clubForm.ClubForm;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.club.clubForm.ClubFormRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.club.clubForm.ClubFormNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.clubForm.RejectReasonNullException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ClubFormService {

    private final MemberRepository memberRepository;
    private final ClubFormRepository clubFormRepository;

    @Transactional
    public void register(ClubFormRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        ClubForm clubForm = ClubFormRequest.toEntity(request,member);
        clubFormRepository.save(clubForm);
    }

    public List<ClubFormDto> findAllById(Long memberId) {
        List<ClubForm> clubForms = clubFormRepository.findByMemberId(memberId);
        return clubForms.stream()
                .map(ClubFormDto::new)
                .toList();
    }

    public List<ClubFormDto> findAll() {
        return clubFormRepository.findAll()
                .stream()
                .map(ClubFormDto::new)
                .toList();
    }

    @Transactional
    public void clubFormChange(Long clubFormId, ClubFormProgressRequest request) {
        ClubForm clubForm = clubFormRepository.findById(clubFormId).orElseThrow(ClubFormNotFoundException::new);
        if(request.getProgress() == ProgressType.APPROVAL){
            clubForm.approve();
        } else if(request.getProgress() == ProgressType.REJECT) {
            if(request.getRejectReason() == null)
                throw new RejectReasonNullException();
            clubForm.reject();
            clubForm.setRejectReason(request.getRejectReason());
        } else {
            throw new IllegalArgumentException("ProgressType is not valid");
        }
    }


}
