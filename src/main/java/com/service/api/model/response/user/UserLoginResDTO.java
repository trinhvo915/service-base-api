package com.service.api.model.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResDTO {
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


    private UserProfileResDTO userInfo;
}
