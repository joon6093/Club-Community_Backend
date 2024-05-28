package org.webppo.clubcommunity_backend.service.board;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.response.exception.board.FileDeleteFailureException;
import org.webppo.clubcommunity_backend.response.exception.board.FileUploadFailureException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@EnableConfigurationProperties(FileProperties.class)
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    private final FileProperties fileProperties;

    @PostConstruct
    void postConstruct() {
        createDirectory(fileProperties.getImage().getLocation());
        createDirectory(fileProperties.getVideo().getLocation());
    }

    private void createDirectory(String location) {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename, String fileType) {
        try {
            Path targetLocation = new File(getLocation(fileType) + filename).toPath();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(String filename, String fileType) {
        File file = new File(getLocation(fileType) + filename);
        boolean deleted = file.delete();
        if (!deleted) {
            throw new FileDeleteFailureException();
        }
    }

    private String getLocation(String fileType) {
        if (fileType.equals("image")) {
            return fileProperties.getImage().getLocation();
        } else if (fileType.equals("video")) {
            return fileProperties.getVideo().getLocation();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
