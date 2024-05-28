package org.webppo.clubcommunity_backend.service.board.image;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.response.exception.board.FileDeleteFailureException;
import org.webppo.clubcommunity_backend.response.exception.board.FileUploadFailureException;
import org.webppo.clubcommunity_backend.service.board.FileProperties;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@EnableConfigurationProperties(FileProperties.class)
@RequiredArgsConstructor
public class LocalImageService implements ImageService {

    private final FileProperties fileProperties;

    @PostConstruct
    void postConstruct() {
        File dir = new File(fileProperties.getImage().getLocation());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename) {
        try {
            Path targetLocation = new File(fileProperties.getImage().getLocation() + filename).toPath();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception e) {
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(String filename) {
        File file = new File(fileProperties.getImage().getLocation() + filename);
        boolean deleted = file.delete();
        if (!deleted) {
            throw new FileDeleteFailureException();
        }
    }
}
