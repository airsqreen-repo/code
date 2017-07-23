package service;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.cron.EventService;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: Bahadir Konu (bahadir.konu@comu.edu.tr)
 * Date: 7/19/2017
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private EventService eventService = new EventService();

    @Mock
    private Sistem9PushEventService sistem9PushEventService;

    @Mock
    private Sistem9PushEventRepository sistem9PushEventRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private Sistem9Adapter sistem9Adapter;

    @Before
    public void setup() {

        eventService.setSistem9PushEventService(sistem9PushEventService);
        eventService.setCampaignRepository(campaignRepository);
        eventService.setSistem9Adapter(sistem9Adapter);
        eventService.setSistem9PushEventRepository(sistem9PushEventRepository);

    }


    @Test
    public void testGenerateSistem9Events() throws Exception {

        when(campaignRepository.findAllActive()).thenReturn(getCampaigns());

        eventService.generateSistem9Events();

        verify(sistem9PushEventService, times(15)).save(
                any(Sistem9PushEventDTO.class)
        );

    }

    @Test
    public void testPushEvents() {

        when(sistem9PushEventRepository.findAll()).thenReturn(getPushEvents());

        eventService.pushEvents();

        verify(sistem9Adapter).push(getPushEvents().get(0));

    }

    private List<Sistem9PushEvent> getPushEvents() {

        Sistem9PushEvent pushEvent = new Sistem9PushEvent();
        pushEvent.setUsername("test");
        pushEvent.setPassword("test");
        pushEvent.setActionId("actionId");
        pushEvent.setDeviceId(1);
        pushEvent.setEventStatus(EventStatus.WAITING);

        return Arrays.asList(pushEvent);

    }

    private List<Campaign> getCampaigns() throws ParseException {

        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.07.2017");
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse("31.07.2017");

        Campaign campaign = new Campaign();

        campaign.setActive(true);
        campaign.setName("Test Campaign");
        campaign.setStartOn(startDate);
        campaign.setEndOn(endDate);

        return Arrays.asList(campaign);
    }

}
