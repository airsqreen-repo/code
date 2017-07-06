package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.security.user.RoleRight;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 5/28/2017.
 */
@Data
public class RoleRightDTO {

    private Long id;
    private Long rightId;
    private Long roleId;
    private RoleDTO role;
    private RightDTO right;

    public static RoleRightDTO toDTO(RoleRight roleRight) {

        RoleRightDTO roleRightDTO = new RoleRightDTO();
        roleRightDTO.setId(roleRight.getId());
        roleRightDTO.setRoleId(roleRight.getRoleId());
        roleRightDTO.setRightId(roleRight.getRightId());
        if(roleRight.getRole() != null )
            roleRightDTO.setRole(RoleDTO.toDTO(roleRight.getRole()));
        if(roleRight.getRight() != null )
            roleRightDTO.setRight(RightDTO.toDTO(roleRight.getRight()));
        return roleRightDTO;
    }

    public static RoleRight toEntity(RoleRightDTO roleRightDTO) {

        RoleRight roleRight = new RoleRight();
        roleRight.setRightId(roleRightDTO.getRightId());
        roleRight.setId(roleRightDTO.getId());
        roleRight.setRoleId(roleRightDTO.getRoleId());
        return roleRight;
    }

    public static RoleRight toEntity( RoleRightDTO roleRightDTO,RoleRight roleRight) {

        roleRight.setRightId(roleRightDTO.getRightId());
        roleRight.setId(roleRightDTO.getId());
        roleRight.setRoleId(roleRightDTO.getRoleId());
        return roleRight;
    }
    public static List<RoleRightDTO> toDTO(List<RoleRight> roleRight) {

        List<RoleRightDTO> roleRightDTOs = new ArrayList<>();
        roleRight.forEach(e -> roleRightDTOs.add(RoleRightDTO.toDTO(e)));
        return roleRightDTOs;
    }

}
