package org.webppo.clubcommunity_backend.dto.club.clubForm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;

@Getter
public class ClubFormProgressRequest {
    @NotNull(message = "진행 상태는 필수 입력 항목입니다.")
    private ProgressType progress;
    @Size(max = 500, message = "거절 사유는 500자를 초과할 수 없습니다.")
    private String rejectReason;
}
