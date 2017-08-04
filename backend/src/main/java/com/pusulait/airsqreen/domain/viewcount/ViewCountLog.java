package com.pusulait.airsqreen.domain.viewcount;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@SQLDelete(sql = "update " + Constants.PREFIX + "VIEW_COUNT_LOG SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Data
@Entity
@Table(name = Constants.PREFIX + "VIEW_COUNT_LOG", indexes = {
        @Index(name = "index_view_count_log_general", columnList = "VIEW_COUNT_ID"),
        @Index(name = "index_view_count_log_general1", columnList = "VIEW_COUNT_ID, UPDATE_DATE, CREATE_DATE, DATA_STATUS")

})
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString
@EqualsAndHashCode
public class ViewCountLog extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIEW_COUNT_LOG_ID")
    @SequenceGenerator(name = "SEQ_VIEW_COUNT_LOG_ID", sequenceName = "SEQ_VIEW_COUNT_LOG_ID", allocationSize = 1, initialValue = 1)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "VIEW_COUNT_ID", nullable = false, insertable = false, updatable = false)
    private ViewCount viewCount;

    @Column(name = "VIEW_COUNT_ID")
    private Long viewCountId;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    /**
     *
     */
    public ViewCountLog() {

    }

    /**
     * @param viewCountId
     * @param description
     */
    public ViewCountLog(Long viewCountId, String description) {
        setViewCountId(viewCountId);
        setDescription(description);
    }

}
