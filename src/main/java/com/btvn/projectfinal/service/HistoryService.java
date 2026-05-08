package com.btvn.projectfinal.service;

import com.btvn.projectfinal.model.dto.AcademicHistoryDTO;
import com.btvn.projectfinal.model.entity.*;
import com.btvn.projectfinal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final MentoringSessionRepository sessionRepository;
    private final AcademicEvaluationRepository evaluationRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final UserRepository userRepository;

    // CORE-07: Truy xuất hồ sơ học thuật đầy đủ bằng JOIN
    public List<AcademicHistoryDTO> getHistory(String studentUsername) {
        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        // JOIN query lấy session + lecturer + profile trong 1 lần
        List<MentoringSession> sessions = sessionRepository
                .findSessionsWithLecturerByStudentId(student.getId());

        List<AcademicHistoryDTO> result = new ArrayList<>();

        for (MentoringSession session : sessions) {
            AcademicHistoryDTO dto = new AcademicHistoryDTO();
            dto.setSessionId(session.getId());
            dto.setScheduledTime(session.getScheduledTime());
            dto.setSessionStatus(session.getStatus().name());

            // Tên giảng viên từ JOIN
            dto.setLecturerName(session.getLecturer().getFullName());

            // Đánh giá nếu có
            evaluationRepository.findBySessionId(session.getId()).ifPresent(ev -> {
                dto.setScore(ev.getScore());
                dto.setComment(ev.getComment());
            });

            // Chi tiết thiết bị đã mượn
            borrowingRecordRepository.findBySessionId(session.getId()).ifPresent(record -> {
                List<AcademicHistoryDTO.BorrowedEquipmentDTO> equipments = new ArrayList<>();
                for (BorrowingDetail detail : record.getDetails()) {
                    AcademicHistoryDTO.BorrowedEquipmentDTO eq = new AcademicHistoryDTO.BorrowedEquipmentDTO();
                    eq.setEquipmentName(detail.getEquipment().getName());
                    eq.setUnit(detail.getEquipment().getUnit());
                    eq.setQuantity(detail.getQuantity());
                    equipments.add(eq);
                }
                dto.setBorrowedEquipments(equipments);
            });

            result.add(dto);
        }
        return result;
    }
}