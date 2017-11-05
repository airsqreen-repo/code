package com.pusulait.airsqreen.service.report;

import com.pusulait.airsqreen.domain.dto.report.EventRunReportDTO;
import com.pusulait.airsqreen.domain.view.EventRunReport;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import com.pusulait.airsqreen.repository.view.EventRunReportRepository;
import com.pusulait.airsqreen.repository.view.SspViewCountLogRepository;
import com.pusulait.airsqreen.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ReportService {

    @Autowired
    private SspViewCountLogRepository sspViewCountLogRepository;

    @Autowired
    private EventRunReportRepository eventRunReportRepository;

    @Inject
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<SspViewCountLog> search(SspViewCountLog sspViewCountLog) {

        log.debug("Request to get all PlatformUsers");

        return sspViewCountLogRepository.search(sspViewCountLog.getSspPrice(), sspViewCountLog.getDeviceId(), sspViewCountLog.getActionId(), sspViewCountLog.getSspDeviceId(), sspViewCountLog.getPlatformUserId());
    }


    @Transactional(readOnly = true)
    public List<EventRunReportDTO> eventRunReportDay(Long campaignId, String from, String to) throws Exception {

        log.debug("Request to get all PlatformUsers");

        String query = "select count(*) as count ,campaign_Id as campaignId ,campaign_name as campaignName, to_char(run_date, 'YYYY-dd-MM')  as runDate from EVENT_RUN_REPORT err where err.event_Status = 'DONE' ";

        if (from != null && to != null) {
            query += " and err.run_Date  between '" + DateUtil.generateStartOrEndDate("start", from) + "' and '" + DateUtil.generateStartOrEndDate("end", to) + "'" ;

        }
        if (campaignId != null) {
            query += " and err.campaign_id = " + campaignId;
        }

        query += " group by campaign_Id,   to_char(run_date, 'YYYY-dd-MM') ,campaign_name";

        Query qt = entityManager.createNativeQuery(query);
        List<Object[]> candidateList = qt.getResultList();

        List<EventRunReportDTO> resultList = new ArrayList<>();
        candidateList.forEach(e -> resultList.add(new EventRunReportDTO((BigInteger) e[1], (BigInteger) e[0], (String) e[3],(String) e[2])));

        return resultList;

    }

    @Transactional(readOnly = true)
    public List<EventRunReportDTO> eventRunReportHour(Long campaignId, String from, String to) throws Exception {

        log.debug("Request to get all PlatformUsers");

        String query = "select count(*) as count , campaign_Id as campaignId ,campaign_name as campaignName, extract(hour from  run_date)  as runDate from EVENT_RUN_REPORT err where err.event_Status = 'WAITING' ";

        if (from != null && to != null) {
            query += " and err.run_Date  between '" + DateUtil.generateStartOrEndDate("start", from) + "' and '" + DateUtil.generateStartOrEndDate("end", to) + "'" ;

        }
        if (campaignId != null) {
            query += " and err.campaign_id = " + campaignId;
        }

        query += " group by campaign_Id,   extract(hour from  run_date), campaign_name";

        Query qt = entityManager.createNativeQuery(query);
        List<Object[]> candidateList = qt.getResultList();

        List<EventRunReportDTO> resultList = new ArrayList<>();
        candidateList.forEach(e -> resultList.add(new EventRunReportDTO((BigInteger) e[1], (BigInteger) e[0], (Double) e[3],(String) e[2])));

        return resultList;

    }


}
