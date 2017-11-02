package com.pusulait.airsqreen.service.report;

import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import com.pusulait.airsqreen.repository.view.SspViewCountLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ReportService {

    @Autowired
    private SspViewCountLogRepository sspViewCountLogRepository;


    @Transactional(readOnly = true)
    public List<SspViewCountLog> search(SspViewCountLog sspViewCountLog) {

        log.debug("Request to get all PlatformUsers");

        return sspViewCountLogRepository.search(sspViewCountLog.getSspPrice(), sspViewCountLog.getDeviceId(), sspViewCountLog.getActionId(), sspViewCountLog.getSspDeviceId(), sspViewCountLog.getPlatformUserId());
    }

}
