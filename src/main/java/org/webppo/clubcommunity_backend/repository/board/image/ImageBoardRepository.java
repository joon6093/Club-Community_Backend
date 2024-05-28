package org.webppo.clubcommunity_backend.repository.board.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.board.image.ImageBoard;

@Repository
public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {
}
