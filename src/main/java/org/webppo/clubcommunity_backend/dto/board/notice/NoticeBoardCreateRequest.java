package org.webppo.clubcommunity_backend.dto.board.notice;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardCreateRequest {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "Club ID cannot be null")
    private Long clubId;
}
