package org.webppo.clubcommunity_backend.repository.board.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.board.video.VideoBoard;

import java.util.List;

@Repository
public interface VideoBoardRepository extends JpaRepository<VideoBoard, Long> {
    List<VideoBoard> findAllByClubId(Long clubId);
}
