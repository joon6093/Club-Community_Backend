package org.webppo.clubcommunity_backend.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

@Getter
@JsonTypeName("true")
public final class SuccessResponseBody<T> extends ResponseBody<T>{
    private final T result;

    public SuccessResponseBody() {
        result = null;
    }

    public SuccessResponseBody(T result) {
        this.result = result;
    }
}
