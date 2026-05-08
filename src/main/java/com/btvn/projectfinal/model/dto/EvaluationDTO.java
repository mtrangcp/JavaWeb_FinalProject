package com.btvn.projectfinal.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class EvaluationDTO {

    private Long sessionId;

    @NotNull(message = "Vui lòng nhập điểm đánh giá")
    @Min(value = 1, message = "Điểm tối thiểu là 1")
    @Max(value = 10, message = "Điểm tối đa là 10")
    private Integer score;

    @Size(max = 1000, message = "Nhận xét không vượt quá 1000 ký tự")
    private String comment;

    // ds thiết bị dc chỉ định
    private List<Long> equipmentIds;
    private List<Integer> quantities;
}