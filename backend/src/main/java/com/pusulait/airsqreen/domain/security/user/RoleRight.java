package com.pusulait.airsqreen.domain.security.user;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


@Slf4j
@Data
@Entity
@ToString(exclude = {"role", "right"})
@EqualsAndHashCode(exclude = {"role", "right"})
@Table(name = Constants.PREFIX + "ROLE_RIGHTS",uniqueConstraints= @UniqueConstraint(columnNames = {"RIGHT_ID", "ROLE_ID"}))

public class RoleRight extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE_RIGHT_ID")
    @SequenceGenerator(name = "SEQ_ROLE_RIGHT_ID", sequenceName = "SEQ_ROLE_RIGHT_ID", allocationSize = 1,initialValue = 2)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RIGHT_ID", nullable = false, insertable = false, updatable = false)
    private Right right;

    @Column(name = "RIGHT_ID")
    private Long rightId;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
    private Role role;

    @Column(name = "ROLE_ID")
    private Long roleId;




}
