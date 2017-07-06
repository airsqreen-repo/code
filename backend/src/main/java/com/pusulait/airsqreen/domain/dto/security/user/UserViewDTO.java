package com.pusulait.airsqreen.domain.dto.security.user;


import com.pusulait.airsqreen.domain.base.DataStatus;
import lombok.Data;

@Data
public class UserViewDTO {

    private Long id;
    private String photoLink;
    private String firstname;
    private String lastname;
    private String nickname;
    private String fullName;
    private String email;
    private DataStatus dataStatus;


}
