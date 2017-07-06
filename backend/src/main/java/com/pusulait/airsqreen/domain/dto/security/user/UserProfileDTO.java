package com.pusulait.airsqreen.domain.dto.security.user;

import com.pusulait.airsqreen.domain.enums.Gender;
import lombok.Data;

import java.util.Date;

/**
 * Created by ferhatyaban on 5/28/2017.
 */
@Data
public class UserProfileDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Date birthDay;
    private Gender gender;
}
