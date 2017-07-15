package com.pusulait.airsqreen.domain.dto.publisher;

import lombok.Data;

/**
 * Created by benan on 7/16/2017.
 */
@Data
public class PublisherDTO {
    private Long id;
    private String name;
    private String code;
    private Boolean filter_anomalies;
    private Long db_cr_number;
    private Long credit_limit;
    private String email;
    private String address;
    private String postal_code;
    private String city;
    private String region;
    private String phone;
    private String mobile;
    private String fax;
    private Long country_id;
    private Boolean use_business_address_for_billing;
    private String billing_address;
    private String billing_postal_code;
    private String billing_city;
    private String billing_region;
    private String billing_phone;
    private String billing_mobile;
    private String billing_fax;
    private String billing_email;
    private Long billing_country_id;
    private String custom_impression_html;
    private String agency_filter;
    private Long[] filtered_agency_ids;
    private String advertiser_filter;
    private Long[] filtered_advertiser_ids;
    private Long[] excluded_offer_ids;
    private String[] excluded_pricing_types;
    private Long[] site_ids;
    private Long[] section_ids;


}
