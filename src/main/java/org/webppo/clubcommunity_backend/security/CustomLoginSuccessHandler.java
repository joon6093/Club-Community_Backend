package org.webppo.clubcommunity_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.webppo.clubcommunity_backend.dto.member.PrincipalDto;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createSuccessResponse;
import static org.webppo.clubcommunity_backend.security.PrincipalHandler.extractName;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(convertToJson(createSuccessResponse(new PrincipalDto(PrincipalHandler.extractId(), extractName(), PrincipalHandler.extractMemberRole()))));
    }

    private String convertToJson(ResponseBody<PrincipalDto> response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}
