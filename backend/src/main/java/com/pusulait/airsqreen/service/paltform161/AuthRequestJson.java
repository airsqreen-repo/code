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
    @JsonProperty(value = "app_id")
    private String appId;
    @JsonProperty(value = "api_key")
    private String apiKey;
    @JsonProperty(value = "app_secret")
    private String appSecret;
    @JsonProperty(value = "user_id")
    private String userId;

}