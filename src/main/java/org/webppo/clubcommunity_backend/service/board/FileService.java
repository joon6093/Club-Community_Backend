package org.webppo.clubcommunity_backend.service.board;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(MultipartFile file, String filename, String fileType);

    void delete(String filename, String fileType);
}
