package org.webppo.clubcommunity_backend.repository.board.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webppo.clubcommunity_backend.entity.board.notice.NoticeBoard;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    List<NoticeBoard> findAllByClubId(Long clubId);
}
