package org.webppo.clubcommunity_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import org.webppo.clubcommunity_backend.response.exception.oauth2.Oauth2LoginFailureException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static org.webppo.clubcommunity_backend.response.ResponseUtil.createFailureResponse;
import static org.webppo.clubcommunity_backend.response.exception.common.ExceptionType.AUTHENTICATION_ENTRY_POINT_EXCEPTION;
import static org.webppo.clubcommunity_backend.response.exception.common.ExceptionType.OAUTH2_LOGIN_FAILURE_EXCEPTION;

@Component
@RequiredArgsConstructor
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(AUTHENTICATION_ENTRY_POINT_EXCEPTION.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
       if (authException instanceof Oauth2LoginFailureException) {
            response.getWriter().write(convertToJson(createFailureResponse(OAUTH2_LOGIN_FAILURE_EXCEPTION)));
        } else {
           response.getWriter().write(convertToJson(createFailureResponse(AUTHENTICATION_ENTRY_POINT_EXCEPTION)));
        }
    }

    private String convertToJson(ResponseBody<Void> response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}
