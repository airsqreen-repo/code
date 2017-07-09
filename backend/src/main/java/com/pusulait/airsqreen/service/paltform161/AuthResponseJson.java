package com.pusulait.airsqreen.service.paltform161;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by benan on 7/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class AuthResponseJson {
    @JsonProperty(value = "access_token")
    private String accessToken;
    private Boolean success;
    private String error;
    private String description;

}
