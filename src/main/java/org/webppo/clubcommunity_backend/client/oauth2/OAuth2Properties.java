package org.webppo.clubcommunity_backend.client.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {
    private Kakao kakao;

    @Getter
    @Setter
    public static class Kakao {
        private String adminKey;
    }
}
