package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    Optional<BorrowingRecord> findBySessionId(Long sessionId);

    List<BorrowingRecord> findByStatusOrderByCreatedAtAsc(BorrowingRecord.BorrowingStatus status);


    @Query("SELECT br FROM BorrowingRecord br " +
            "JOIN FETCH br.details bd " +
            "JOIN FETCH bd.equipment " +
            "WHERE br.status = 'PENDING_ISSUE'")
    List<BorrowingRecord> findPendingWithDetails();
}