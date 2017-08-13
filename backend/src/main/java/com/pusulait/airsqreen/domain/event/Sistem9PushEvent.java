package com.pusulait.airsqreen.domain.event;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by benan on 7/16/2017.
 */

@Data
@Entity
@Table(name = Constants.PREFIX + "SISTEM9_PUSH_EVENTS")
public class Sistem9PushEvent extends BaseEvent {

    @Column(name = "CAMPAIGN_SECTION_ID")
    private Long campaignSectionId;

    @JoinColumn(name = "CAMPAIGN_SECTION_ID", insertable = false, updatable = false)
    @ManyToOne
    private CampaignSection campaignSection;

    @JoinColumn(name = "PLATFORM_USER_ID", insertable = false, updatable = false)
    @ManyToOne
    private PlatformUser platformUser;

    @Column(name = "PLATFORM_USER_ID")
    private Long platformUserId;

    @JoinColumn(name = "DEVICE_ID", insertable = false, updatable = false)
    @ManyToOne
    private Device device;

    @Column(name = "DEVICE_ID")
    private Long deviceId;

    @Column(name = "ACTION_ID")
    private String actionId;


}
