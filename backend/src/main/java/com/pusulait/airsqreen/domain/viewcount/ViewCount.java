package com.pusulait.airsqreen.domain.viewcount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Constraints;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@SQLDelete(sql = "update " + Constants.PREFIX + "VIEW_COUNT SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Data
@Entity
@Table(name = Constants.PREFIX + "VIEW_COUNT", indexes = {
        @Index(name = "index_view_count_with_token", columnList = "TRKNG_TOKEN"),
        @Index(name = "index_view_count_general", columnList = "CAMPAIGN_ID, CMPGN_SCTN_ID"),
        @Index(name = "index_view_count_general1", columnList = "CAMPAIGN_ID, DEVICE_ID, ACTION_ID"),
        @Index(name = "index_view_count_general2", columnList = "CAMPAIGN_ID, CMPGN_SCTN_ID, UPDATE_DATE, CREATE_DATE, DATA_STATUS")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CAMPAIGN_ID", "CMPGN_SCTN_ID", "DEVICE_ID", "ACTION_ID"}, name = Constraints.UC_VIEW_COUNT_GNRL_UQC),
        @UniqueConstraint(columnNames = {"TRKNG_TOKEN"}, name = Constraints.UC_VIEW_COUNT_TRCK_TOKEN)
})
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString(exclude = {"countLogs "})
@EqualsAndHashCode(exclude = {"countLogs "})
//@JsonIgnoreProperties({"countLogs"})
public class ViewCount extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIEW_COUNT_ID")
    @SequenceGenerator(name = "SEQ_VIEW_COUNT_ID", sequenceName = "SEQ_VIEW_COUNT_ID", allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "CAMPAIGN_ID")
    private String campaignId;
    @Column(name = "CMPGN_SCTN_ID")
    private String campaignSectionId;
    @Column(name = "DEVICE_ID")
    private String deviceId;
    @Column(name = "ACTION_ID")
    private String actionId;
    @Column(name = "TRKNG_TOKEN")
    private String trackingToken;
    @Column(name = "BCKND_SND_TRC_URL", length = 1500)
    private String backendTrackUrl;
    @Column(name = "TOTAL_COUNT")
    private Long totalCount;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "viewCount")
    private List<ViewCountLog> countLogs;
}
