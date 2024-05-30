package org.webppo.clubcommunity_backend.entity.club;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.webppo.clubcommunity_backend.dto.club.ClubUpdateRequest;
import org.webppo.clubcommunity_backend.entity.common.EntityDate;
import org.webppo.clubcommunity_backend.entity.member.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Club extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;
    @Column(nullable = false)
    private String clubName;
    @Column
    private String clubIntroduction;
    @Column
    private String clubHistory;
    @Setter
    @Column
    private String clubImageName; // 대표사진 파일명 또는 URL
    @Column
    private String meetingTime; // 정기모임 시간
    @Column
    private String president;
    @Column
    private String vicePresident;
    @Column
    private String secretary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member clubMaster;
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Register> clubMembers = new ArrayList<>();


    public Club(String clubName, Member clubMaster) {
        this.clubName = clubName;
        this.clubMaster = clubMaster;
        this.addMember(clubMaster);
    }

    public void addMember(Member member) {
        Register register = new Register(this, member);
        this.clubMembers.add(register);
        member.getClubs().add(register);
    }

    public void removeMember(Member member) {
        Register register = this.clubMembers.stream()
                .filter(r -> r.getMember().equals(member))
                .findFirst()
                .orElse(null);
        if (register != null) {
            this.clubMembers.remove(register);
            member.getClubs().remove(register);
        }
    }

    public void update(ClubUpdateRequest request) {
        this.clubName = request.getClubName();
        this.clubIntroduction = request.getClubIntroduction();
        this.clubHistory = request.getClubHistory();
        this.meetingTime = request.getMeetingTime();
        this.president = request.getPresident();
        this.vicePresident = request.getVicePresident();
        this.secretary = request.getSecretary();
    }

}
