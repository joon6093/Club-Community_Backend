package org.webppo.clubcommunity_backend.security.response;

import org.webppo.clubcommunity_backend.response.exception.oauth2.Oauth2LoginFailureException;

import java.util.Map;

import static org.webppo.clubcommunity_backend.security.response.KakaoConstants.KAKAO_REGISTRATION_ID;

public class OAuth2ResponseFactory {
    public static OAuth2Response getOAuth2Response(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId.toLowerCase()) {
            case KAKAO_REGISTRATION_ID -> new KakaoResponse(attributes);
            default -> throw new Oauth2LoginFailureException("Unsupported provider: " + registrationId);
        };
    }
}
