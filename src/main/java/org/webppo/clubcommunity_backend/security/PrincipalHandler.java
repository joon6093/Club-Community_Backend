package org.webppo.clubcommunity_backend.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalHandler {

    public static Long extractId() {
        return getUserDetails().getId();
    }

    public static String extractName() {
        return getUserDetails().getName();
    }

    public static RoleType extractMemberRole() {
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> RoleType.valueOf(authority.getAuthority()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No roles found for the user"));
    }

    private static CustomOAuth2UserDetails getUserDetails() {
        return (CustomOAuth2UserDetails) getAuthentication().getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
