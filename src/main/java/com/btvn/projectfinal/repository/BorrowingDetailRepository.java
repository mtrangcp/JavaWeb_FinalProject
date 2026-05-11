package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.BorrowingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingDetailRepository extends JpaRepository<BorrowingDetail, Long> {
    List<BorrowingDetail> findByBorrowingRecordId(Long recordId);

    boolean existsByEquipmentId(Long equipmentId);
}