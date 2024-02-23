package com.uahannam.mediation.repository;

import com.uahannam.mediation.domain.MediationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediationEventRepository extends JpaRepository<MediationEvent, Long> {
    Optional<MediationEvent> findByEventUUID(String eventUUID);
}
