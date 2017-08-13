package com.pusulait.airsqreen.domain.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ferhatyaban on 24/07/2017.
 */
@Data
@Entity
@Table(name = "VIEW_ACTIVE_DEVICES")
@Immutable
public class ViewActiveDevice implements Serializable {

    @Column(name = "DEVICE_ID")
    private Long deviceId;



}
