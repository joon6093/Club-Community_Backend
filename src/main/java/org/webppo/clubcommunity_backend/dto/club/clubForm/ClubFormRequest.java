package org.webppo.clubcommunity_backend.dto.club.clubForm;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.club.clubForm.ClubForm;
import org.webppo.clubcommunity_backend.entity.member.Member;

@Getter
public class ClubFormRequest {
    @NotBlank(message = "동아리 유형은 필수 입력 항목입니다.")
    private String clubType; // 중앙 / 학과
    @NotBlank(message = "동아리 이름은 필수 입력 항목입니다.")
    private String clubName;
    @NotBlank(message = "신청 부서는 필수 입력 항목입니다.")
    private String applicantDepartment;
    @NotBlank(message = "지도교수 이름은 필수 입력 항목입니다.")
    private String advisorName;
    @NotBlank(message = "지도교수 전공은 필수 입력 항목입니다.")
    private String advisorMajor;
    @NotBlank(message = "지도교수 연락처는 필수 입력 항목입니다.")
    private String advisorContact;

    public static ClubForm toEntity(ClubFormRequest request, Member member) {
        return ClubForm.builder()
                .clubName(request.getClubName())
                .clubType(request.getClubType())
                .applicantDepartment(request.getApplicantDepartment())
                .advisorName(request.getAdvisorName())
                .advisorMajor(request.getAdvisorMajor())
                .advisorContact(request.getAdvisorContact())
                .progress(ProgressType.REVIEW)
                .member(member)
                .build();
    }
}
