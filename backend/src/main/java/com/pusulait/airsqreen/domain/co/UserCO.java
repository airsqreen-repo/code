package com.pusulait.airsqreen.domain.co;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by TRB on 08.06.2017.
 */

@Data
public class UserCO implements Serializable {

    private String email = null;
    private String firstname = null;
    private String lastname = null;

}