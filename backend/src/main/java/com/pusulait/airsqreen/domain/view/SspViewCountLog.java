package com.pusulait.airsqreen.domain.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
@Entity
@Table(name = "ssp_view_count_log")
@Immutable
public class SspViewCountLog extends SspViewCount implements Serializable {


    @Column(name = "update_date", updatable = false, nullable = false)
    private Date updateDate;


}
