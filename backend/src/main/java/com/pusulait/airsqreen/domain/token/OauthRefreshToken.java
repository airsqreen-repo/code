package com.pusulait.airsqreen.domain.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 06.07.2017.
 */
@Data
@Entity
@Table(name = "OAUTH_REFRESH_TOKEN")
@ToString
@EqualsAndHashCode
public class OauthRefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Lob
    @Column(name = "TOKEN")
    private byte[] token;

    @Lob
    @Column(name = "AUTHENTICATION")
    private byte[] authentication;


}
