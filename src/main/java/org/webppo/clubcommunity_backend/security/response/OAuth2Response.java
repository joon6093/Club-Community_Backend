package org.webppo.clubcommunity_backend.security.response;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getName();
    String getProfileImage();
}
