package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.security.user.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 5/28/2017.
 */
@Data
public class RoleDTO {

    private Long id;
    private String name;
    private List<RoleRightDTO> roleRightList = new ArrayList<RoleRightDTO>();
    public static RoleDTO toDTO(Role role) {

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setId(role.getId());
        return roleDTO;
    }
    public static RoleDTO toDTOWithRights(Role role) {

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setId(role.getId());
        if(role.getRoleRightList()!=null)
            roleDTO.setRoleRightList(RoleRightDTO.toDTO(role.getRoleRightList()));
        return roleDTO;
    }
    public static Role toEntity(RoleDTO roleDTO) {

        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setId(roleDTO.getId());
        return role;
    }

    public static Role toEntity(RoleDTO roleDTO,Role role) {
        role.setName(roleDTO.getName());
        role.setId(roleDTO.getId());
        return role;
    }
}
