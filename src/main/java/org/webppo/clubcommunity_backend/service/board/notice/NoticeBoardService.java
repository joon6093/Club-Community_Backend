package org.webppo.clubcommunity_backend.service.board.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardCreateRequest;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardDto;
import org.webppo.clubcommunity_backend.dto.board.notice.NoticeBoardUpdateRequest;
import org.webppo.clubcommunity_backend.entity.board.notice.NoticeBoard;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.repository.board.notice.NoticeBoardRepository;
import org.webppo.clubcommunity_backend.repository.club.ClubRepository;
import org.webppo.clubcommunity_backend.repository.member.MemberRepository;
import org.webppo.clubcommunity_backend.response.exception.board.BoardNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.ClubNotFoundException;
import org.webppo.clubcommunity_backend.response.exception.club.NotClubMasterException;
import org.webppo.clubcommunity_backend.response.exception.club.NotClubMemberException;
import org.webppo.clubcommunity_backend.response.exception.member.MemberNotFoundException;
import org.webppo.clubcommunity_backend.security.PrincipalHandler;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @Transactional
    public NoticeBoardDto create(Long memberId, NoticeBoardCreateRequest req) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Club club = clubRepository.findById(req.getClubId()).orElseThrow(ClubNotFoundException::new);
        checkClubMaster(member, club);

        NoticeBoard noticeBoard = NoticeBoard.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .member(member)
                .club(club)
                .build();

        noticeBoardRepository.save(noticeBoard);

        return new NoticeBoardDto(noticeBoard.getId(), noticeBoard.getTitle(), noticeBoard.getContent());
    }

    @Transactional
    public NoticeBoardDto update(Long id, NoticeBoardUpdateRequest req) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, noticeBoard.getClub());

        noticeBoard.update(member, req);
        noticeBoardRepository.save(noticeBoard);

        return new NoticeBoardDto(noticeBoard.getId(), noticeBoard.getTitle(), noticeBoard.getContent());
    }

    public NoticeBoardDto read(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMember(member, noticeBoard.getClub());

        return new NoticeBoardDto(noticeBoard.getId(), noticeBoard.getTitle(), noticeBoard.getContent());
    }

    public List<NoticeBoardDto> readAll(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMember(member, club);
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAllByClubId(clubId);

        return noticeBoards.stream()
                .map(board -> new NoticeBoardDto(board.getId(), board.getTitle(), board.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findById(PrincipalHandler.extractId()).orElseThrow(MemberNotFoundException::new);
        checkClubMaster(member, noticeBoard.getClub());

        noticeBoardRepository.delete(noticeBoard);
    }

    private void checkClubMaster(Member member, Club club) {
        if (!club.getClubMaster().equals(member)) {
            throw new NotClubMasterException();
        }
    }

    private void checkClubMember(Member member, Club club) {
        boolean isMember = member.getClubs().stream()
                .anyMatch(register -> register.getClub().equals(club));
        if (!isMember) {
            throw new NotClubMemberException();
        }
    }
}
