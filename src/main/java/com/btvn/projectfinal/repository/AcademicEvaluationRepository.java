package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.AcademicEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicEvaluationRepository extends JpaRepository<AcademicEvaluation, Long> {
    Optional<AcademicEvaluation> findBySessionId(Long sessionId);
}