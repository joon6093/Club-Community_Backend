package org.webppo.clubcommunity_backend.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.webppo.clubcommunity_backend.entity.member.Member;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MemberDto {
    private Long id;
    private String name;
    private String username;
    private String profileImage;
    private RoleType role;
    private LocalDateTime birthDate;
    private String gender;
    private String department;
    private String studentId;
    private String phoneNumber;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getName(),
                member.getUsername(),
                member.getProfileImage(),
                member.getRole(),
                member.getBirthDate(),
                member.getGender(),
                member.getDepartment(),
                member.getStudentId(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt());
    }
}
