package org.webppo.clubcommunity_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.webppo.clubcommunity_backend.response.ResponseBody;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createFailureResponse;
import static org.webppo.clubcommunity_backend.response.exception.common.ExceptionType.ACCESS_DENIED_EXCEPTION;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(ACCESS_DENIED_EXCEPTION.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(convertToJson(createFailureResponse(ACCESS_DENIED_EXCEPTION)));
    }

    private String convertToJson(ResponseBody<Void> response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}
