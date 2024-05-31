package org.webppo.clubcommunity_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.webppo.clubcommunity_backend.service.board.FileProperties;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class WebConfig implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageLocation = fileProperties.getImage().getLocation();
        if (imageLocation != null && !imageLocation.isEmpty()) {
            registry.addResourceHandler("/image/**")
                    .addResourceLocations("file:" + imageLocation)
                    .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic());
        }

        String videoLocation = fileProperties.getVideo().getLocation();
        if (videoLocation != null && !videoLocation.isEmpty()) {
            registry.addResourceHandler("/video/**")
                    .addResourceLocations("file:" + videoLocation)
                    .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic());
        }

        String fileLocation = fileProperties.getFile().getLocation();
        if (fileLocation != null && !fileLocation.isEmpty()) {
            registry.addResourceHandler("/file/**")
                    .addResourceLocations("file:" + fileLocation)
                    .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic());
        }
    }
}
