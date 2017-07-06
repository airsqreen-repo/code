package com.pusulait.airsqreen.domain.enums;

/**
 */
public enum Language {

    TURKISH("TR"), ENGLISH("EN"), DEUSTCHLAND("DE"), SPAIN("SP"), ITALY("IT"), RUSSIA("RU");

    private String langKey;

    Language(String langKey){
        this.langKey = langKey;
    }

    public String getLangKey(){
        return langKey;
    }

}
