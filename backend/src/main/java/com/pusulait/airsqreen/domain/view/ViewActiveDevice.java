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
@Table(name = "VIEW_ACTIVE_DEVICES")
@Immutable
public class ViewActiveDevice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEVICE_ID", updatable = false, nullable = false)
    private Long deviceId;




}
