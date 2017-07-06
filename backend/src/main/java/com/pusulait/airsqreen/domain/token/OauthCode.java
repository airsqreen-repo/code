package com.pusulait.airsqreen.domain.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 06.07.2017.
 */
@Data
@Entity
@Table(name = "OAUTH_CODE")
@ToString
@EqualsAndHashCode
public class OauthCode {

    @Id
    @Column(name = "CODE")
    private String code;


}
