package org.webppo.clubcommunity_backend.response.exception.oauth2;
import org.springframework.security.core.AuthenticationException;

public class Oauth2LoginFailureException extends AuthenticationException {
    public Oauth2LoginFailureException(String message) {
        super(message);
    }
}
