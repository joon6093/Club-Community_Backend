package org.webppo.clubcommunity_backend.service.board.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void upload(MultipartFile file, String filename);

    void delete(String filename);
}
