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
    @JsonProperty(value = "token")
    private String token;
    @JsonProperty(value = "active")
    private Boolean active;
    private String result;
    private String type;
    private String message;

}