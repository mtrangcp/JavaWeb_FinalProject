package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    List<Lecturer> findByDepartmentId(Long departmentId);

    Optional<Lecturer> findByUserId(Long userId);

    Optional<Lecturer> findByUserUsername(String username);

    // Lấy lecturer + profile
    @Query("SELECT l FROM Lecturer l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH u.profile " +
            "WHERE l.department.id = :deptId")
    List<Lecturer> findByDepartmentIdWithProfile(@Param("deptId") Long deptId);
}
