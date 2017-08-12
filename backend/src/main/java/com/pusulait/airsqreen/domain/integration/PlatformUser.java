package com.pusulait.airsqreen.domain.integration;


import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.EventType;
import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Slf4j
@Data
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_USERS")
@SQLDelete(sql="update " + Constants.PREFIX +"PLATFORM_USERS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Where(clause = "DATA_STATUS <> 'DELETED'")
public class PlatformUser extends AuditBase implements Serializable {

    private static final long serialVersionUID = -687874117917352477L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLATFORM_USER_ID")
    @SequenceGenerator(name = "SEQ_PLATFORM_USER_ID", sequenceName = "SEQ_PLATFORM_USER_ID", allocationSize = 1,initialValue = 2)
    private Long id;

    @NotNull
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "SECRET")
    private String secret;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PLATFORM_TYPE")
    private PlatformType platformType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "SERVICE_TYPE")
    private ServiceType serviceType;



}
