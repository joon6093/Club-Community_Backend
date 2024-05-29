package org.webppo.clubcommunity_backend.dto.board.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoardDto {
    private Long id;
    private String title;
    private String content;
}
