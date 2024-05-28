package org.webppo.clubcommunity_backend.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.club.Register;
import org.webppo.clubcommunity_backend.entity.club.clubForm.ClubForm;
import org.webppo.clubcommunity_backend.entity.common.EntityDate;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String profileImage;

    @Column
    private LocalDateTime birthDate;

    @Column
    private String gender;

    @Column
    private String department;

    @Column
    private String studentId;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @OneToMany(mappedBy = "member")
    private List<Register> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubForm> clubForms = new ArrayList<>();

    @Builder
    public Member(String name, String password, String username, String profileImage, LocalDateTime birthDate, String gender, String department, String studentId, String phoneNumber, String email, RoleType role) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.profileImage = profileImage;
        this.birthDate = birthDate;
        this.gender = gender;
        this.department = department;
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public void updateAdditionalInfo(LocalDateTime birthDate, String gender, String department, String studentId, String phoneNumber, String email, RoleType role) {
        this.birthDate = birthDate;
        this.gender = gender;
        this.department = department;
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public void updateInfo(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }
}
