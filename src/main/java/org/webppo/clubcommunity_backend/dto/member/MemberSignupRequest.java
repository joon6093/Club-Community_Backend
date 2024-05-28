package org.webppo.clubcommunity_backend.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Profile image is mandatory")
    private String profileImage;

    @Past(message = "Birth date must be in the past")
    private LocalDateTime birthDate;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotBlank(message = "Student ID is mandatory")
    private String studentId;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
}
