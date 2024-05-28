package org.webppo.clubcommunity_backend.dto.board.video;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoBoardCreateRequest {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Club ID cannot be null")
    private Long clubId;

    @NotEmpty(message = "Images cannot be empty")
    private List<MultipartFile> videos;
}
