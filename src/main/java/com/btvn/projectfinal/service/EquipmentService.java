package com.btvn.projectfinal.service;

import com.btvn.projectfinal.model.dto.EquipmentDTO;
import com.btvn.projectfinal.model.entity.Equipment;
import com.btvn.projectfinal.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public Page<Equipment> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isBlank()) {
            return equipmentRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return equipmentRepository.findAll(pageable);
    }

    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }

    @Transactional
    public void save(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        mapDtoToEntity(dto, equipment);
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void update(Long id, EquipmentDTO dto) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy thiết bị!"));
        mapDtoToEntity(dto, equipment);
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void delete(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy thiết bị!");
        }
        equipmentRepository.deleteById(id);
    }

    private void mapDtoToEntity(EquipmentDTO dto, Equipment entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setUnit(dto.getUnit());
        entity.setStatus(dto.getStatus() != null
                ? dto.getStatus()
                : Equipment.EquipmentStatus.AVAILABLE);
    }
}