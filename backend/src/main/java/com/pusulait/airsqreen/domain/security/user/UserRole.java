package com.pusulait.airsqreen.domain.security.user;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@Entity

@Table(name = Constants.PREFIX + "USER_ROLES",uniqueConstraints= @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID","VERSION"}))
@SQLDelete(sql = "update " + Constants.PREFIX + "USER_ROLES SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString(exclude = {"role","user"})
@EqualsAndHashCode(exclude = {"role","user"})

public class UserRole extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ROLE_ID")
    @SequenceGenerator(name = "SEQ_USER_ROLE_ID", sequenceName = "SEQ_USER_ROLE_ID", allocationSize = 1,initialValue =2)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
    private Role role;

    @Column(name = "ROLE_ID")
    private Long roleId;


}



