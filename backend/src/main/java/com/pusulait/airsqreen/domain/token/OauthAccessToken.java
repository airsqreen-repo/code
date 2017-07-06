package com.pusulait.airsqreen.domain.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by user on 06.07.2017.
 */
@Data
@Entity
@Table(name = "OAUTH_ACCESS_TOKEN")
@ToString
@EqualsAndHashCode
public class OauthAccessToken {
    @Id
    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Lob
    @Column(name = "TOKEN")
    private byte[] token;

    @Column(name = "AUTHENTICATION_ID")
    private String authenticationId;


    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Lob
    @Column(name = "AUTHENTICATION")
    private byte[] authentication;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;


}