package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.security.user.UserRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 5/28/2017.
 */
@Data
public class UserRoleDTO {

    private Long id;
    private Long userId;
    private Long roleId;
    private RoleDTO role;

    public static UserRoleDTO toDTO(UserRole userRole) {

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setId(userRole.getId());
        userRoleDTO.setRoleId(userRole.getRoleId());
        userRoleDTO.setUserId(userRole.getUserId());
        userRoleDTO.setRole(RoleDTO.toDTO(userRole.getRole()));
        return userRoleDTO;
    }
    public static List<UserRoleDTO> toDTO(List<UserRole> userRoles) {

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        userRoles.forEach(e -> userRoleDTOs.add(UserRoleDTO.toDTO(e)));
        return userRoleDTOs;
    }
    public static UserRole toEntity(UserRoleDTO userRoleDTO) {

        UserRole userRole = new UserRole();
        userRole.setUserId(userRoleDTO.getUserId());
        userRole.setId(userRoleDTO.getId());
        userRole.setRoleId(userRoleDTO.getRoleId());
        return userRole;
    }
    public static UserRole toEntity(UserRole userRole, UserRoleDTO userRoleDTO) {
        userRole.setUserId(userRoleDTO.getUserId());
        userRole.setId(userRoleDTO.getId());
        userRole.setRoleId(userRoleDTO.getRoleId());
        return userRole;
    }
}
