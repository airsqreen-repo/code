package com.pusulait.airsqreen.domain.audit;

import com.pusulait.airsqreen.config.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Persist AuditEvent managed by the Spring Boot actuator
 *
 * @see org.springframework.boot.actuate.audit.AuditEvent
 */
@Data
@Entity
@Table(
    name = Constants.PREFIX + "AUDIT_EVENTS",
    indexes = {
        @Index(name = "index_user_username", columnList = "principal,event_date")})
@ToString
@EqualsAndHashCode
public class PersistentAuditEvent {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT_ID")
    @SequenceGenerator(name = "SEQ_EVENT_ID", sequenceName = "SEQ_EVENT_ID", allocationSize = 1)
    private Long id;


    @NotNull
    @Column(nullable = false)
    private String principal;

    @Column(name = "event_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime auditEventDate;

    @Column(name = "event_type")
    private String auditEventType;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name =  Constants.PREFIX + "AUDIT_EVT_DATAS" , joinColumns = @JoinColumn(name = "event_id"))
    private Map<String, String> data = new HashMap<>();

}
