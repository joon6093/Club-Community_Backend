package org.webppo.clubcommunity_backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

@Data
@AllArgsConstructor
public class PrincipalDto {
    private Long memberId;
    private String name;
    private RoleType role;
}
