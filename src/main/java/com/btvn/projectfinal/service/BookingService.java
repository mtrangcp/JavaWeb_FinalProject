package com.btvn.projectfinal.service;

import com.btvn.projectfinal.model.dto.BookingDTO;
import com.btvn.projectfinal.model.entity.Lecturer;
import com.btvn.projectfinal.model.entity.MentoringSession;
import com.btvn.projectfinal.model.entity.User;
import com.btvn.projectfinal.repository.LecturerRepository;
import com.btvn.projectfinal.repository.MentoringSessionRepository;
import com.btvn.projectfinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MentoringSessionRepository sessionRepository;
    private final LecturerRepository lecturerRepository;
    private final UserRepository userRepository;

    @Transactional
    public void book(String studentUsername, BookingDTO dto) {
        if (!dto.getScheduledTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Thời gian đặt lịch phải ở tương lai!");
        }

        // check xung đột giờ của gv
        boolean conflict = sessionRepository.existsByLecturerIdAndScheduledTimeAndStatusNot(
                dto.getLecturerId(),
                dto.getScheduledTime(),
                MentoringSession.SessionStatus.CANCELLED
        );
        if (conflict) {
            throw new IllegalArgumentException(
                    "Giảng viên đã có lịch vào khung giờ này! Vui lòng chọn giờ khác.");
        }

        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        MentoringSession session = new MentoringSession();
        session.setStudent(student);
        session.setLecturer(lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên!")));
        session.setScheduledTime(dto.getScheduledTime());
        session.setNote(dto.getNote());
        session.setStatus(MentoringSession.SessionStatus.PENDING);

        sessionRepository.save(session);
    }

    public List<Lecturer> getLecturersByDepartment(Long departmentId) {
        return lecturerRepository.findByDepartmentIdWithProfile(departmentId);
    }
}