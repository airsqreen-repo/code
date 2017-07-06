package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.security.user.Right;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 5/28/2017.
 */
@Data
public class RightDTO {

    private Long id;
    private String name;

    public static RightDTO toDTO(Right right) {

        RightDTO rightDTO = new RightDTO();
        rightDTO.setId(right.getId());
        rightDTO.setName(right.getName());
        return rightDTO;
    }

    public static Right toEntity(RightDTO rightDTO) {
        Right right = new Right();
        right.setId(rightDTO.getId());
        right.setName(rightDTO.getName());
        return right;
    }

    public static Right toEntity(Right right, RightDTO rightDTO) {

        right.setId(rightDTO.getId());
        right.setName(rightDTO.getName());
        return right;
    }
    public static List<RightDTO> toDTO(List<Right> right) {

        List<RightDTO> rightDTOs = new ArrayList<>();
        right.forEach(e -> rightDTOs.add(RightDTO.toDTO(e)));
        return rightDTOs;
    }

}
