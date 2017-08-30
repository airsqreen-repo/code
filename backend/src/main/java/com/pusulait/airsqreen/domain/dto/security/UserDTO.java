package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.security.user.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by benan on 5/28/2017.
 */
@Data
public class UserDTO {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean activated = false;
    private String phoneNumber;

    private DataStatus dataStatus;
    private List<UserRoleDTO> userRoleList;

    public static UserDTO toDTOWithRoles(User user) {
        UserDTO userDTO =   UserDTO.toDTO(user);
        userDTO.setUserRoleList(
                UserRoleDTO.toDTO(user.getUserRoleList().stream()
                        .filter(p -> p.getDataStatus() != DataStatus.DELETED)
                        .collect(Collectors.toList())
                ));
        return userDTO;
    }
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setActivated(user.getActivated());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setDataStatus(user.getDataStatus());
        return userDTO;
    }
    public static User toEntity(UserDTO userDTO) {

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setActivated(userDTO.getActivated());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }

    public static User toEntity(User user,UserDTO userDTO) {

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setActivated(userDTO.getActivated());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }

}
