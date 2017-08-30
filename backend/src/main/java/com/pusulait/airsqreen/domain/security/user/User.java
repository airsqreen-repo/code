package com.pusulait.airsqreen.domain.security.user;


import com.pusulait.airsqreen.annotation.trim.Trim;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Constraints;
import com.pusulait.airsqreen.config.constants.Indexes;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.Language;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Email;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A user.
 */
@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "USERS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "USERS")
@ApiModel(description = "User Model")
public class User extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ID")
    @SequenceGenerator(name = "SEQ_USER_ID", sequenceName = "SEQ_USER_ID", allocationSize = 1, initialValue = 2)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME", unique = true, nullable = false, length = 50)
    private String username;

    @NotNull
    @Size(min = 6, max = 60)
    @Column(name = "PASSWORD",length = 60)
    private String password;


    @Trim
    @ApiModelProperty(position = 2, required = true)
    @Size(min = 2, max = 30)
    @Column(name = "FIRSTNAME")
    @NotNull
    private String firstname;

    @Trim
    @ApiModelProperty(position = 3, required = true)
    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "LASTNAME")
    private String lastname;

    @Trim
    @ApiModelProperty(position = 4, required = true)
    @NotNull
    @Size(min = 2, max = 150)
    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "ACTIVATED", nullable = false)
    private Boolean activated = false;

    @Size(max = 20)
    @Column(name = "ACTIVATION_KEY", length = 20)
    @JsonIgnore
    private String activationKey;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;


    /*ferhat*/

    @Column(name = "AND_REG_ID")
    private String androidRegId;

    @Column(name = "IPHONE_REG_ID")
    private String iphoneRegId;

   // @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_LANGUAGE", nullable = true, columnDefinition =
            "varchar(100) CONSTRAINT USER_DEFAULT_LANGUAGE_CHECK CHECK (DEFAULT_LANGUAGE::text = ANY (ARRAY['TURKISH'::character varying, 'ENGLISH'::character varying, 'DEUSTCHLAND'::character varying, 'SPAIN'::character varying, 'ITALY'::character varying, 'RUSSIA'::character varying]::text[]))")
    private Language language = Language.TURKISH;




    @JsonIgnore
    @RestResource(exported = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> userRoleList = new ArrayList<UserRole>();


}
