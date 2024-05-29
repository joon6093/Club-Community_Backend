package org.webppo.clubcommunity_backend.repository.board.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webppo.clubcommunity_backend.entity.board.recruitment.RecruitmentBoard;

import java.util.List;

public interface RecruitmentBoardRepository extends JpaRepository<RecruitmentBoard, Long> {
    List<RecruitmentBoard> findAllByClubId(Long clubId);
}
