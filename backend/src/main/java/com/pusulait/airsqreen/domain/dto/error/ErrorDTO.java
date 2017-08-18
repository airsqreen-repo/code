package com.pusulait.airsqreen.domain.dto.error;


import lombok.Data;


@Data
public class ErrorDTO {

    private String error;
    private String error_description;

    public ErrorDTO(String error, String error_description){
        this.setError(error);
        this.setError_description(error_description);
    }
}
