package org.webppo.clubcommunity_backend.dto.board.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentBoardDto {
    private Long id;
    private String title;
    private String content;
}
