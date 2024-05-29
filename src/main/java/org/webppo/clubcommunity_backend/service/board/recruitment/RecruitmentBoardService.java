package org.webppo.clubcommunity_backend.service.board.recruitment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardDto;
import org.webppo.clubcommunity_backend.dto.board.recruitment.RecruitmentBoardUpdateRequest;
import org.webppo.clubcommunity_backend.entity.board.recruitment.RecruitmentBoard;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.board.recruitment.RecruitmentBoardRepository;
import org.webppo.clubcommunity_backend.repository.club.ClubRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.board.BoardNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.ClubNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.NotClubMasterException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentBoardService {

    private final RecruitmentBoardRepository recruitmentBoardRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @Transactional
    public RecruitmentBoardDto create(Long memberId, RecruitmentBoardCreateRequest req) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Club club = clubRepository.findById(req.getClubId()).orElseThrow(ClubNotFoundException::new);
        checkClubMaster(member, club);

        RecruitmentBoard recruitmentBoard = RecruitmentBoard.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .member(member)
                .club(club)
                .build();

        recruitmentBoardRepository.save(recruitmentBoard);

        return new RecruitmentBoardDto(recruitmentBoard.getId(), recruitmentBoard.getTitle(), recruitmentBoard.getContent());
    }

    @Transactional
    public RecruitmentBoardDto update(Long id, RecruitmentBoardUpdateRequest req) {
        RecruitmentBoard recruitmentBoard = recruitmentBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, recruitmentBoard.getClub());

        recruitmentBoard.update(member, req);
        recruitmentBoardRepository.save(recruitmentBoard);

        return new RecruitmentBoardDto(recruitmentBoard.getId(), recruitmentBoard.getTitle(), recruitmentBoard.getContent());
    }

    public RecruitmentBoardDto read(Long id) {
        RecruitmentBoard recruitmentBoard = recruitmentBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        return new RecruitmentBoardDto(recruitmentBoard.getId(), recruitmentBoard.getTitle(), recruitmentBoard.getContent());
    }

    public List<RecruitmentBoardDto> readAll(Long clubId) {
        List<RecruitmentBoard> recruitmentBoards = recruitmentBoardRepository.findAllByClubId(clubId);

        return recruitmentBoards.stream()
                .map(board -> new RecruitmentBoardDto(board.getId(), board.getTitle(), board.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        RecruitmentBoard recruitmentBoard = recruitmentBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, recruitmentBoard.getClub());

        recruitmentBoardRepository.delete(recruitmentBoard);
    }

    private void checkClubMaster(Member member, Club club) {
        if (!club.getClubMaster().equals(member)) {
            throw new NotClubMasterException();
        }
    }
}
