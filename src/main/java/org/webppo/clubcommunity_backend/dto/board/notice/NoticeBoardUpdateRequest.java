package org.webppo.clubcommunity_backend.dto.board.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardUpdateRequest {

    private String title;

    private String content;
}
