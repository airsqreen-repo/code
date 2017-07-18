package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.security.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by benan on 7/13/2017.
 */


@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "INVENTORIES SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "INVENTORIES")
@Inheritance(strategy = InheritanceType.JOINED)
public class Inventory extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.INVENTORY_SEQUENCE)
    @SequenceGenerator(name = Sequences.INVENTORY_SEQUENCE, sequenceName = Sequences.INVENTORY_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "EXTERNAL_ID")
    private Long externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_ID", nullable = false, insertable = false, updatable = false)
    private Campaign campaign;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "BUDGET")
    private Double budget;


}
