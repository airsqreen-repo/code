package com.pusulait.airsqreen.domain.dto.event;

import com.pusulait.airsqreen.config.constants.Constants;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by benan on 7/16/2017.
 */

@Data
@Entity
@DiscriminatorValue(value = "SISTEM9_PUSH")
public class Sistem9PushEvent extends BaseEvent {

}
