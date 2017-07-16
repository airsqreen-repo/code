package com.pusulait.airsqreen.domain.campaign.platform161;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Inventory;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by benan on 7/15/2017.
 */
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_INVENTORIES")
@TypeDefs(value = {
        @TypeDef(name = "text", typeClass = StringArrayType.class)
        , @TypeDef(name = "longarray", typeClass = LongArrayType.class)
})
@Data
public class Plt161Inventory extends Inventory {


    @Type(type = "text")
    @Column(columnDefinition = "text")
    private List<Long> inventory;

    @Column(name = "DOMAIN_FILTER")
    private String domain_filter;


    @Type(type = "text")
    @Column(columnDefinition = "text")
    private List<Long> domains;

}
