package com.pusulait.airsqreen.domain.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import sun.util.calendar.LocalGregorianCalendar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 06.07.2017.
 */
@Data
@Entity
@Table(name = "OAUTH_APPROVALS")
@ToString
@EqualsAndHashCode
public class OauthApprovals implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TOKEN_ID")
    private String userId;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "SCOPE")
    private String scope;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EXPIRESAT", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date expiresAt;


    @Column(name = "LASTMODIFIEDAT", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastModifiedAt;


}
