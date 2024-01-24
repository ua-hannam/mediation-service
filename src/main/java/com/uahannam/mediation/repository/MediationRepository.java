package com.uahannam.mediation.repository;

import com.uahannam.mediation.domain.Mediation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediationRepository extends JpaRepository<Mediation, Long> {
}
