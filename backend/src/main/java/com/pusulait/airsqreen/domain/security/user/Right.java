package com.pusulait.airsqreen.domain.security.user;


import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SQLDelete(sql="update " + Constants.PREFIX +"RIGHTS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Data
@Entity
@Table(name = Constants.PREFIX + "RIGHTS",indexes = {@Index(name = "index_right_name", columnList = "name")})
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString(exclude = {"roleRightList"}, callSuper = true)
@EqualsAndHashCode(exclude = {"roleRightList"}, callSuper = true)
public class Right extends AuditBase implements Serializable {

    private static final long serialVersionUID = -687874117917352477L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RIGHT_ID")
    @SequenceGenerator(name = "SEQ_RIGHT_ID", sequenceName = "SEQ_RIGHT_ID", allocationSize = 1,initialValue = 2)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;


    @JsonIgnore
    @RestResource(exported = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "right")
    private List<RoleRight> roleRightList  = new ArrayList<>();

}
