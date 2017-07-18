package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bhdr on 16.07.2017.
 *
 * Documentation: GET v2/inventory_sources
 *
 */
@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "INVENTORY_SOURCES2 SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "INVENTORY_SOURCES2")
public class InventorySource2 extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.INVENTORY_SOURCE2_SEQUENCE)
    @SequenceGenerator(name = Sequences.INVENTORY_SOURCE2_SEQUENCE, sequenceName = Sequences.INVENTORY_SOURCE2_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;


}
