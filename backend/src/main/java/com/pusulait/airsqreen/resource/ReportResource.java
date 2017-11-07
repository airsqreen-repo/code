package com.pusulait.airsqreen.resource;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.report.SspViewCountDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.domain.view.EventRunReport;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.report.ReportService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/report", produces = "application/hal+json")
@Slf4j
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SystemErrorService systemErrorService;

    @RequestMapping(value = "/sspViewCountLog/last30day", method = RequestMethod.GET)
    public ResponseEntity<?> sspViewCountLoglast30day() {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            long addTime=now.getTime() -  Constants.ONE_DAY * 30;
            String from =sdf.format(new Date(addTime));
            String to= sdf.format(now);

            List<SspViewCountDTO> list= reportService.last30Day(from,to);
            if(list.size() == 0) {
                 addTime=now.getTime()- Constants.ONE_DAY * 90;
                 from =sdf.format(new Date(addTime));
                 list= reportService.last30Day(from,to);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.SSPVIEWCOUNTLOGSEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("reportService.sspViewCountLogSearch", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    //

    @RequestMapping(value = "/sspViewCountLogs", method = RequestMethod.GET)
    public ResponseEntity<?> sspViewCountLogs(@RequestParam(value="deviceId", required=false)Long deviceId, @RequestParam(value="from", required=false) String from, @RequestParam(value="to", required=false) String to, @ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {
            return new ResponseEntity<>(assembler.toResource(reportService.search(deviceId,from,to, pageable)), HttpStatus.OK);
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
