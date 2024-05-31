package org.webppo.clubcommunity_backend.service.club;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.club.ClubDto;
import org.webppo.clubcommunity_backend.dto.club.ClubUpdateRequest;
import org.webppo.clubcommunity_backend.dto.member.MemberDto;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.club.Register;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.club.ClubRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.club.ClubNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.NotClubMasterException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.service.board.LocalFileService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final LocalFileService localFileService;

    @Transactional
    public Boolean isMaster(Long memberId, Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        return isMasterCheck(memberId, club);
    }

    public static Boolean isMasterCheck(Long memberId, Club club) {
        if(!club.getClubMaster().getId().equals(memberId)) {
            throw new NotClubMasterException();
        }
        return true;
    }

    public ClubDto findById(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        return new ClubDto(club);
    }

    public List<ClubDto> findAll() {
        return clubRepository.findAll().stream()
                .map(ClubDto::new)
                .toList();
    }

    @Transactional
    public void updateClub(ClubUpdateRequest request, Long memberId, Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        if(isMasterCheck(memberId, club)) {
            club.update(request);
            MultipartFile clubPhoto = request.getClubPhoto();
            if (!clubPhoto.isEmpty()) {
                String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(clubPhoto.getOriginalFilename()));
                String fileName = UUID.randomUUID() + "_" + originalFilename;
                localFileService.upload(clubPhoto, fileName, "image");
                club.setClubImageName(fileName);
            }
        }
    }



    public List<ClubDto> findMemberId(Long memberId) {
        return clubRepository.findAll().stream()
                .filter(club -> club.getClubMembers().stream()
                        .anyMatch(register -> register.getMember().getId().equals(memberId)))
                .map(ClubDto::new)
                .toList();
    }

    public List<MemberDto> findClubMembers(Long clubId, Long memberId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        isMasterCheck(memberId, club);
        return club.getClubMembers().stream()
                .map(Register::getMember)
                .map(MemberDto::from)
                .toList();
    }

    @Transactional
    public void deleteClubMember(Long clubId, Long memberId, Long masterId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(isMasterCheck(masterId, club)) {
            club.removeMember(member);
        }
    }


}
