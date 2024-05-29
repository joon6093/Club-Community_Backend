package org.webppo.clubcommunity_backend.dto.board.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentBoardUpdateRequest {

    private String title;

    private String content;
}
