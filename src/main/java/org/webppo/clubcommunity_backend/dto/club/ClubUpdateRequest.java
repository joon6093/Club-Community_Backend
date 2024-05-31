package org.webppo.clubcommunity_backend.dto.club;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubUpdateRequest {
    @NotEmpty(message = "동아리 이름은 빈 값일 수 없습니다.")
    private String clubName;

    @Size(max = 500, message = "동아리 소개는 최대 500자까지 입력할 수 있습니다.")
    private String clubIntroduction;

    @Size(max = 500, message = "동아리 활동 내역은 최대 500자까지 입력할 수 있습니다.")
    private String clubHistory;

    private MultipartFile clubPhoto;

    @Size(max = 50, message = "정기 모임 시간은 최대 50자까지 입력할 수 있습니다.")
    private String meetingTime;

    @Size(max = 20, message = "동아리 회장 이름은 최대 20자까지 입력할 수 있습니다.")
    private String president;

    @Size(max = 20, message = "동아리 부회장 이름은 최대 20자까지 입력할 수 있습니다.")
    private String vicePresident;

    @Size(max = 20, message = "동아리 총무 이름은 최대 20자까지 입력할 수 있습니다.")
    private String secretary;

}
