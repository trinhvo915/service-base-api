package com.service.api.model.response.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class JWTToken {
    private String accessToken;

    private String tokenType;

    @JsonProperty("access_token")
    public String getAccessToken() {
        return this.accessToken;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return this.tokenType;
    }
}
