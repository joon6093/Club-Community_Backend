package org.webppo.clubcommunity_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "frontend")
public class FrontendProperties {
    private String url;
}
