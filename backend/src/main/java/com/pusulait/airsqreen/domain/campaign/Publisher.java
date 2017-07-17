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
 * Created by bhdr on 17.07.2017.
 */
@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "PUBLISHERS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "PUBLISHERS")
public class Publisher extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.PUBLISHER_SEQUENCE)
    @SequenceGenerator(name = Sequences.PUBLISHER_SEQUENCE, sequenceName = Sequences.PUBLISHER_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name="FILTER_ANOMALIES")
    private Boolean filter_anomalies;

    @Column
    private Long db_cr_number;

    @Column
    private Long credit_limit;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String postal_code;

    @Column
    private String city;

    @Column
    private String region;

    @Column
    private String phone;

    @Column
    private String mobile;

    @Column
    private String fax;

    @Column
    private Long country_id;

    @Column
    private Boolean use_business_address_for_billing;

    @Column
    private String billing_address;

    @Column
    private String billing_postal_code;

    @Column
    private String billing_city;

    @Column
    private String billing_region;

    @Column
    private String billing_phone;

    @Column
    private String billing_mobile;

    @Column
    private String billing_fax;

    @Column
    private String billing_email;

    @Column
    private Long billing_country_id;

    @Column
    private String custom_impression_html;

    @Column
    private String agency_filter;

    @Column
    private Long[] filtered_agency_ids;

    @Column
    private String advertiser_filter;

    @Column
    private Long[] filtered_advertiser_ids;

    @Column
    private Long[] excluded_offer_ids;

    @Column
    private String[] excluded_pricing_types;

    @Column
    private Long[] site_ids;

    @Column
    private Long[] section_ids;


}
