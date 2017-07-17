package com.pusulait.airsqreen.domain.dto.publisher;

import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Publisher;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
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

    public static Plt161Publisher toEntity(PublisherDTO publisherDTO) {

        Plt161Publisher publisher = new Plt161Publisher();

        publisher.setName(publisherDTO.getName());
        publisher.setCode(publisherDTO.getCode());
        publisher.setFilter_anomalies(publisherDTO.getFilter_anomalies());
        publisher.setDb_cr_number(publisherDTO.getDb_cr_number());
        publisher.setCredit_limit(publisherDTO.getCredit_limit());
        publisher.setEmail(publisherDTO.getEmail());
        publisher.setAddress(publisherDTO.getAddress());
        publisher.setPostal_code(publisherDTO.getPostal_code());
        publisher.setCity(publisherDTO.getCity());
        publisher.setRegion(publisherDTO.getRegion());
        publisher.setPhone(publisherDTO.getPhone());
        publisher.setMobile(publisherDTO.getMobile());
        publisher.setFax(publisherDTO.getFax());
        publisher.setCountry_id(publisherDTO.getCountry_id());
        publisher.setUse_business_address_for_billing(publisherDTO.getUse_business_address_for_billing());
        publisher.setBilling_address(publisherDTO.getBilling_address());
        publisher.setBilling_postal_code(publisherDTO.getBilling_postal_code());
        publisher.setBilling_city(publisherDTO.getBilling_city());
        publisher.setBilling_region(publisherDTO.getBilling_region());
        publisher.setBilling_phone(publisherDTO.getBilling_phone());
        publisher.setBilling_mobile(publisherDTO.getBilling_mobile());
        publisher.setBilling_fax(publisherDTO.getBilling_fax());
        publisher.setBilling_email(publisherDTO.getBilling_email());
        publisher.setBilling_country_id(publisherDTO.getBilling_country_id());
        publisher.setCustom_impression_html(publisher.getCustom_impression_html());
        publisher.setAgency_filter(publisher.getAgency_filter());
        publisher.setFiltered_agency_ids(publisher.getFiltered_agency_ids());
        publisher.setAdvertiser_filter(publisher.getAdvertiser_filter());
        publisher.setFiltered_advertiser_ids(publisher.getFiltered_advertiser_ids());
        publisher.setExcluded_offer_ids(publisher.getExcluded_offer_ids());
        publisher.setExcluded_pricing_types(publisher.getExcluded_pricing_types());
        publisher.setSite_ids(publisher.getSite_ids());
        publisher.setSection_ids(publisher.getSection_ids());

        return publisher;
    }

    public static Plt161Publisher toEntity(PublisherDTO publisherDTO,Plt161Publisher publisher) {

        publisher = new Plt161Publisher();

        publisher.setName(publisherDTO.getName());
        publisher.setCode(publisherDTO.getCode());
        publisher.setFilter_anomalies(publisherDTO.getFilter_anomalies());
        publisher.setDb_cr_number(publisherDTO.getDb_cr_number());
        publisher.setCredit_limit(publisherDTO.getCredit_limit());
        publisher.setEmail(publisherDTO.getEmail());
        publisher.setAddress(publisherDTO.getAddress());
        publisher.setPostal_code(publisherDTO.getPostal_code());
        publisher.setCity(publisherDTO.getCity());
        publisher.setRegion(publisherDTO.getRegion());
        publisher.setPhone(publisherDTO.getPhone());
        publisher.setMobile(publisherDTO.getMobile());
        publisher.setFax(publisherDTO.getFax());
        publisher.setCountry_id(publisherDTO.getCountry_id());
        publisher.setUse_business_address_for_billing(publisherDTO.getUse_business_address_for_billing());
        publisher.setBilling_address(publisherDTO.getBilling_address());
        publisher.setBilling_postal_code(publisherDTO.getBilling_postal_code());
        publisher.setBilling_city(publisherDTO.getBilling_city());
        publisher.setBilling_region(publisherDTO.getBilling_region());
        publisher.setBilling_phone(publisherDTO.getBilling_phone());
        publisher.setBilling_mobile(publisherDTO.getBilling_mobile());
        publisher.setBilling_fax(publisherDTO.getBilling_fax());
        publisher.setBilling_email(publisherDTO.getBilling_email());
        publisher.setBilling_country_id(publisherDTO.getBilling_country_id());
        publisher.setCustom_impression_html(publisher.getCustom_impression_html());
        publisher.setAgency_filter(publisher.getAgency_filter());
        publisher.setFiltered_agency_ids(publisher.getFiltered_agency_ids());
        publisher.setAdvertiser_filter(publisher.getAdvertiser_filter());
        publisher.setFiltered_advertiser_ids(publisher.getFiltered_advertiser_ids());
        publisher.setExcluded_offer_ids(publisher.getExcluded_offer_ids());
        publisher.setExcluded_pricing_types(publisher.getExcluded_pricing_types());
        publisher.setSite_ids(publisher.getSite_ids());
        publisher.setSection_ids(publisher.getSection_ids());

        return publisher;
    }

}
