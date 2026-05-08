package com.btvn.projectfinal.service;

import com.btvn.projectfinal.model.entity.*;
import com.btvn.projectfinal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final EquipmentRepository equipmentRepository;

    public List<BorrowingRecord> getPendingRecords() {
        return borrowingRecordRepository.findPendingWithDetails();
    }

    //Xác nhận xuất kho
    @Transactional
    public void confirmIssue(Long recordId) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn!"));

        // check tồn kho
        for (BorrowingDetail detail : record.getDetails()) {
            Equipment equipment = detail.getEquipment();
            if (equipment.getQuantity() < detail.getQuantity()) {
                throw new IllegalStateException(
                        "Thiết bị '" + equipment.getName() + "' không đủ số lượng! " +
                                "Tồn kho: " + equipment.getQuantity() +
                                ", Cần: " + detail.getQuantity()
                );
            }
        }

        //  trừ kho
        for (BorrowingDetail detail : record.getDetails()) {
            Equipment equipment = detail.getEquipment();
            equipment.setQuantity(equipment.getQuantity() - detail.getQuantity());
            equipmentRepository.save(equipment);
        }

        record.setStatus(BorrowingRecord.BorrowingStatus.ISSUED);
        borrowingRecordRepository.save(record);
    }
}