package org.webppo.clubcommunity_backend.dto.club.clubJoinForm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.webppo.clubcommunity_backend.entity.club.ProgressType;
import org.webppo.clubcommunity_backend.entity.club.clubJoinForm.ClubJoinForm;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ClubJoinFormResponse {
    private Long id;
    private String clubName;
    private String username;
    private String filename;
    private ProgressType progress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public ClubJoinFormResponse(ClubJoinForm clubJoinForm) {
        this.id = clubJoinForm.getId();
        this.clubName = clubJoinForm.getClub().getClubName();
        this.username = clubJoinForm.getMember().getUsername();
        this.filename = clubJoinForm.getFilename();
        this.progress = clubJoinForm.getProgress();
        this.createdAt = clubJoinForm.getCreatedAt();
        this.modifiedAt = clubJoinForm.getModifiedAt();
    }
}
