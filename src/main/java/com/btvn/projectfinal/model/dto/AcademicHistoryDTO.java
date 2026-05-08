package com.btvn.projectfinal.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AcademicHistoryDTO {

    private Long sessionId;
    private LocalDateTime scheduledTime;
    private String lecturerName;
    private String sessionStatus;

    //  đánh giá
    private Integer score;
    private String comment;

    // ds thiết bị đã mượn
    private List<BorrowedEquipmentDTO> borrowedEquipments;

    @Data
    public static class BorrowedEquipmentDTO {
        private String equipmentName;
        private String unit;
        private Integer quantity;
    }
}