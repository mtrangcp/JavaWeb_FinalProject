package com.btvn.projectfinal.service;

import com.btvn.projectfinal.model.dto.EvaluationDTO;
import com.btvn.projectfinal.model.entity.*;
import com.btvn.projectfinal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final MentoringSessionRepository sessionRepository;
    private final AcademicEvaluationRepository evaluationRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BorrowingDetailRepository borrowingDetailRepository;
    private final EquipmentRepository equipmentRepository;
    private final LecturerRepository lecturerRepository;

    @Transactional
    public void evaluate(EvaluationDTO dto) {
        // session → COMPLETED
        MentoringSession session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch tư vấn!"));

        if (session.getStatus() != MentoringSession.SessionStatus.PENDING) {
            throw new IllegalStateException("Lịch tư vấn này không ở trạng thái chờ xác nhận!");
        }
        session.setStatus(MentoringSession.SessionStatus.COMPLETED);
        sessionRepository.save(session);

        //lưu đánh giá
        AcademicEvaluation evaluation = new AcademicEvaluation();
        evaluation.setSession(session);
        evaluation.setScore(dto.getScore());
        evaluation.setComment(dto.getComment());
        evaluationRepository.save(evaluation);

        // tạo phiếu mượn + chi tiết
        if (dto.getEquipmentIds() != null && !dto.getEquipmentIds().isEmpty()) {
            BorrowingRecord record = new BorrowingRecord();
            record.setSession(session);
            record.setStatus(BorrowingRecord.BorrowingStatus.PENDING_ISSUE);
            borrowingRecordRepository.save(record);

            List<BorrowingDetail> details = new ArrayList<>();
            for (int i = 0; i < dto.getEquipmentIds().size(); i++) {
                Long equipmentId = dto.getEquipmentIds().get(i);
                Integer qty = dto.getQuantities().get(i);

                if (qty <= 0) continue;

                Equipment equipment = equipmentRepository.findById(equipmentId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị!"));

                BorrowingDetail detail = new BorrowingDetail();
                detail.setBorrowingRecord(record);
                detail.setEquipment(equipment);
                detail.setQuantity(qty);
                details.add(detail);
            }
            borrowingDetailRepository.saveAll(details);
        }
    }

    public List<MentoringSession> getPendingSessions(String lecturerUsername) {
        return sessionRepository.findByLecturerIdAndStatusOrderByScheduledTimeAsc(
                getLecturerIdByUsername(lecturerUsername),
                MentoringSession.SessionStatus.PENDING
        );
    }

    private Long getLecturerIdByUsername(String username) {
        // Tìm lecturer qua user
        return lecturerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên!"))
                .getId();
    }
}