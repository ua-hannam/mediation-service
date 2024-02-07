package com.uahannam.mediation.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "MEDIATION_EVENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediationEvent extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDIATION_EVENT_ID")
    private Long mediationEventId;

    @Column(name = "MEDIATION_EVENT_UUID")
    private String eventUUID;

    @Column(name = "MEDIATION_ID")
    private Long mediationId;
}
