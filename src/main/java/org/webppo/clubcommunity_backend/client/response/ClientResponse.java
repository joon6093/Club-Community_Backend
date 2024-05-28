package org.webppo.clubcommunity_backend.client.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponse<T> {
    private T data;

    public static <T> ClientResponse<T> success(T data) {
        return new ClientResponse<>(data);
    }

    public static <T> ClientResponse<T> failure() {
        return new ClientResponse<>(null);
    }

    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }
}
