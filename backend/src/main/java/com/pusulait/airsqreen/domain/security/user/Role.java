package com.pusulait.airsqreen.domain.security.user;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Constraints;
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
@SQLDelete(sql="update " +  Constants.PREFIX  + "roles SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Data
@Entity
@Table(name = Constants.PREFIX + "ROLES",indexes = {@Index(name = "index_role_name", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"}, name = Constraints.UC_ROLE_NAME)}
)
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString(exclude = {"userRoleList ","roleRightList"} )
@EqualsAndHashCode(exclude = {"roleRightList ","roleRightList"} )
public class Role extends AuditBase implements Serializable {

    private static final long serialVersionUID = -687874117917352477L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE_ID")
    @SequenceGenerator(name = "SEQ_ROLE_ID", sequenceName = "SEQ_ROLE_ID", allocationSize = 1, initialValue = 2)
    private Long id;

    @NotNull
    @Column(name = "NAME",nullable = false)
    private String name;

    @JsonIgnore
    @RestResource(exported = false)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    private List<RoleRight> roleRightList = new ArrayList<RoleRight>();

    @RestResource(exported = false)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    private List<UserRole> userRoleList = new ArrayList<UserRole>();


}
