package org.webppo.clubcommunity_backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.webppo.clubcommunity_backend.config.FrontendProperties;
import org.webppo.clubcommunity_backend.entity.member.type.RoleType;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(FrontendProperties.class)
public class CustomOAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final FrontendProperties frontendProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        RoleType role = PrincipalHandler.extractMemberRole();
        String targetUrl;

        String frontendUrl = frontendProperties.getUrl();

        if (RoleType.ROLE_PENDING.equals(role)) {
            targetUrl = frontendUrl + "/signupAddition";
        } else if (RoleType.ROLE_USER.equals(role)) {
            targetUrl = frontendUrl;
        } else if (RoleType.ROLE_ADMIN.equals(role)) {
            targetUrl = frontendUrl + "/admin";
        } else {
            targetUrl = frontendUrl + "/login";
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
