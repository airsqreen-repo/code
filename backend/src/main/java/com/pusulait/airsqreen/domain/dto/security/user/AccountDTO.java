package com.pusulait.airsqreen.domain.dto.security.user;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.enums.Language;
import lombok.Data;
import java.util.List;

/**
 * Created by TRB on 6.6.2017.
 */
@Data
public class AccountDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String photoLink;
    private Language defaultLanguage;
    private Boolean activated;

    private String nickname;
    private Boolean permissionEmail;
    private Boolean permissionBabyName;
    private Boolean permissionBabyGender;
    private Boolean appNotifications;
    private String androidRegId;
    private Boolean photoAllowed;
    private DataStatus dataStatus;

    private List<String> roles;


    public AccountDTO(Long id, String firstname, String lastname, String email, Boolean activated, String nickname, List<String> roles) {
        this.setId(id);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setRoles(roles);
        this.setPhotoLink(photoLink);
        this.setDefaultLanguage(defaultLanguage);
        this.setActivated(activated);
        this.setNickname(nickname);
        this.setPermissionEmail(permissionEmail);
        this.setPermissionBabyName(permissionBabyName);
        this.setPermissionBabyGender(permissionBabyGender);
        this.setAppNotifications(appNotifications);
        this.setAndroidRegId(androidRegId);
        this.setPhotoAllowed(photoAllowed);
    }
}
