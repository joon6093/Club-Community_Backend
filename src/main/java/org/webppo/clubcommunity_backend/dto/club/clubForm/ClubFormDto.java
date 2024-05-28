package org.webppo.clubcommunity_backend.dto.club.clubForm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.club.clubForm.ClubForm;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClubFormDto {
    private Long id;
    private String clubType; // 중앙 / 학과
    private String clubName;
    private String applicantDepartment;
    private String advisorName;
    private String advisorMajor;
    private String advisorContact;
    private ProgressType progress;
    private String rejectReason;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public ClubFormDto(ClubForm clubForm) {
        this.id = clubForm.getId();
        this.clubType = clubForm.getClubType();
        this.clubName = clubForm.getClubName();
        this.applicantDepartment = clubForm.getApplicantDepartment();
        this.advisorName = clubForm.getAdvisorName();
        this.advisorMajor = clubForm.getAdvisorMajor();
        this.advisorContact = clubForm.getAdvisorContact();
        this.progress = clubForm.getProgress();
        this.rejectReason = clubForm.getRejectReason();
        this.username = clubForm.getMember().getUsername();
        this.createdAt = clubForm.getCreatedAt();
        this.modifiedAt = clubForm.getModifiedAt();
    }
}
