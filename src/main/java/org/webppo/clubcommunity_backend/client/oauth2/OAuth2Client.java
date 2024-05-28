package org.webppo.clubcommunity_backend.client.oauth2;

public abstract class OAuth2Client {
    public abstract void unlink(String identifier);
    protected abstract String getSupportedProvider();
    public final boolean supports(String provider) {
        return getSupportedProvider().equalsIgnoreCase(provider);
    }
}
