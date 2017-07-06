package com.pusulait.airsqreen.domain.dto.security.user;

import com.pusulait.airsqreen.domain.enums.Language;
import lombok.Data;


/**
 * Created by ferhatyaban on 3.7.2017.
 */
@Data
public class RegisterDTO {

    private String username;
    private String email;
    private String password;
    private Language language;
    private String iphoneRegId;
    private String androidRegId;


}
