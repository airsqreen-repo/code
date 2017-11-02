package com.pusulait.airsqreen.resource;

import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SystemErrorService systemErrorService;

    @RequestMapping(value = "/report/sspViewCountLog", method = RequestMethod.GET)
    public ResponseEntity<?> sspViewCountLogSearch(@RequestBody SspViewCountLog sspViewCountLog) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            return new ResponseEntity<>(reportService.search(sspViewCountLog), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.SSPVIEWCOUNTLOGSEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("reportService.sspViewCountLogSearch", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
