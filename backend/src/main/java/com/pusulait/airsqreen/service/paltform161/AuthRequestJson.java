package com.pusulait.airsqreen.service.paltform161;

/**
 * Created by benan on 7/9/2017.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auth Request json object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class AuthRequestJson {
    @JsonProperty(value = "user")
    private String user;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "client_id")
    private String clientId;
    @JsonProperty(value = "client_secret")
    private String clientSecret;

}