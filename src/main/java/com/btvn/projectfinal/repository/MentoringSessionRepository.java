package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.MentoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MentoringSessionRepository extends JpaRepository<MentoringSession, Long> {
    boolean existsByLecturerIdAndScheduledTimeAndStatusNot(
            Long lecturerId,
            LocalDateTime scheduledTime,
            MentoringSession.SessionStatus status
    );

    boolean existsByStudentIdAndScheduledTimeAndStatusNot(
            Long studentId,
            LocalDateTime scheduledTime,
            MentoringSession.SessionStatus status
    );

    // Lấy ds lịch của sv
    List<MentoringSession> findByStudentIdOrderByScheduledTimeDesc(Long studentId);

    List<MentoringSession> findByLecturerIdAndStatusOrderByScheduledTimeAsc(
            Long lecturerId,
            MentoringSession.SessionStatus status
    );


    @Query("SELECT ms FROM MentoringSession ms " +
            "JOIN FETCH ms.lecturer l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH u.profile " +
            "WHERE ms.student.id = :studentId " +
            "ORDER BY ms.scheduledTime DESC")
    List<MentoringSession> findSessionsWithLecturerByStudentId(@Param("studentId") Long studentId);
}