package com.pusulait.airsqreen.domain;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Data
@Entity
@SQLDelete(sql = "update " + Constants.PREFIX + "SYSTEM_ERRORS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Table(name = Constants.PREFIX + "SYSTEM_ERRORS")
public class SystemError extends AuditBase implements Serializable {

    private static final long serialVersionUID = -687874117917352477L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYSTEM_ERROR_ID")
    @SequenceGenerator(name = "SEQ_SYSTEM_ERROR_ID", sequenceName = "SEQ_SYSTEM_ERROR_ID", allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "ERROR_TYPE")
    @Enumerated(EnumType.STRING)
    private ErrorType errorType;

    @Lob
    @Column(name = "ERROR_DESCRIPTION")
    private String errorDescription;

    @Column(name = "USERNAME")
    private String username;
}
