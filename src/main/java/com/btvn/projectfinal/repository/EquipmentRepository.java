package com.btvn.projectfinal.repository;

import com.btvn.projectfinal.model.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Page<Equipment> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Equipment> findByStatus(Equipment.EquipmentStatus status);
}
