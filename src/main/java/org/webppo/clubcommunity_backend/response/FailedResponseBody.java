package org.webppo.clubcommunity_backend.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

@Getter
@JsonTypeName("false")
public final class FailedResponseBody<T> extends ResponseBody<T> {

    private final String msg;

    public FailedResponseBody(String code, String msg) {
        super.setCode(code);
        this.msg = msg;
    }
}
