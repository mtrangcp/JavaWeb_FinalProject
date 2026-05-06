package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.LabRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRoomTypeRepository extends JpaRepository<LabRoomType, Long> {
}
