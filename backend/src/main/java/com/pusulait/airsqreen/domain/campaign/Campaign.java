package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by benan on 7/13/2017.
 */

@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "CAMPAIGNS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "CAMPAIGNS")
@ApiModel(description = "User Model")
public class Campaign extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAMPAIGN_ID")
    @SequenceGenerator(name = "SEQ_CAMPAIGN_ID", sequenceName = "SEQ_CAMPAIGN_ID", allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "EXTERNAL_ID")
    private Long externalId;




}
