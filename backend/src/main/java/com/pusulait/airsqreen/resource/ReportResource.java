package com.pusulait.airsqreen.resource;

import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.domain.view.EventRunReport;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/report", produces = "application/hal+json")
@Slf4j
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SystemErrorService systemErrorService;

    @RequestMapping(value = "/sspViewCountLog", method = RequestMethod.GET)
    public ResponseEntity<?> sspViewCountLogSearch(@RequestBody SspViewCountLog sspViewCountLog) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            return new ResponseEntity<>(reportService.search(sspViewCountLog), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.SSPVIEWCOUNTLOGSEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("reportService.sspViewCountLogSearch", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/eventViewReportDay", method = RequestMethod.GET)
    public ResponseEntity<?> eventViewReport(@RequestParam(value="campaignId", required=false)Long campaignId, @RequestParam(value="from", required=false) String from, @RequestParam(value="to", required=false) String to) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            return new ResponseEntity<>(reportService.eventRunReportDay(campaignId,from,to), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.SSPVIEWCOUNTLOGSEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("reportService.sspViewCountLogSearch", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(value = "/eventViewReportHour", method = RequestMethod.GET)
    public ResponseEntity<?> eventViewReportHour(@RequestParam(value="campaignId", required=false)Long campaignId, @RequestParam(value="from", required=false) String from, @RequestParam(value="to", required=false) String to) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            return new ResponseEntity<>(reportService.eventRunReportHour(campaignId,from,to), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.SSPVIEWCOUNTLOGSEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("reportService.sspViewCountLogSearch", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

}
