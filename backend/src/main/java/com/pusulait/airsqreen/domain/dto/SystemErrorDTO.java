package com.pusulait.airsqreen.domain.dto;

import com.pusulait.airsqreen.domain.SystemError;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import lombok.Data;

/**
 */
@Data
public class SystemErrorDTO {

    private Long id;
    private ErrorType errorType;
    private String username;

    public SystemErrorDTO(ErrorType errorType, String username){
        this.errorType = errorType;
        this.username = username;
    }

    SystemErrorDTO(){
    }


    public static SystemError convertSystemError(SystemErrorDTO systemErrorDTO) {

        SystemError systemError = new SystemError();
        systemError.setId(systemErrorDTO.getId());
        systemError.setErrorType(systemErrorDTO.getErrorType());
        systemError.setUsername(systemErrorDTO.getUsername());
        return systemError;

    }

}
