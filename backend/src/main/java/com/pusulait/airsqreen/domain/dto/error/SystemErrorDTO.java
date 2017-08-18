package com.pusulait.airsqreen.domain.dto.error;

import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.domain.error.SystemError;
import lombok.Data;

/**
 */
@Data
public class SystemErrorDTO {

    private Long id;
    private ErrorType errorType;
    private String errorDescription;
    private String username;

    public SystemErrorDTO(String errorDescription,ErrorType errorType, String username){
        this.errorType = errorType;
        this.username = username;
        this.errorDescription = errorDescription;
    }


    SystemErrorDTO(){
    }


    public static SystemError toEntity(SystemErrorDTO systemErrorDTO) {

        SystemError systemError = new SystemError();
        systemError.setId(systemErrorDTO.getId());
        systemError.setErrorType(systemErrorDTO.getErrorType());
        systemError.setUsername(systemErrorDTO.getUsername());
        systemError.setErrorDescription(systemErrorDTO.getErrorDescription());
        return systemError;

    }

}
