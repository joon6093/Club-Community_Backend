package org.webppo.clubcommunity_backend.dto.club;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.entity.club.Club;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ClubDto {
    private Long id;
    private String clubName;
    private String clubIntroduction;
    private String clubHistory;
    private String clubPhotoUrl;
    private String meetingTime;
    private String president;
    private String vicePresident;
    private String secretary;
    private String clubMasterName;
    private String clubJoinFormUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public ClubDto(Club club) {
        this.id = club.getId();
        this.clubName = club.getClubName();
        this.clubIntroduction = club.getClubIntroduction();
        this.clubHistory = club.getClubHistory();
        this.clubPhotoUrl = club.getClubImageName();
        this.meetingTime = club.getMeetingTime();
        this.president = club.getPresident();
        this.vicePresident = club.getVicePresident();
        this.secretary = club.getSecretary();
        this.clubMasterName = club.getClubMaster().getUsername();
        this.clubJoinFormUrl = club.getClubJoinFormName();
        this.createdAt = club.getCreatedAt();
        this.modifiedAt = club.getModifiedAt();
    }
}
