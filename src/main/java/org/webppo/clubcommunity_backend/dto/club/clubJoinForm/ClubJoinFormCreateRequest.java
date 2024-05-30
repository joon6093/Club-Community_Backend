package org.webppo.clubcommunity_backend.dto.club.clubJoinForm;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubJoinFormCreateRequest {

    private MultipartFile file;
}
