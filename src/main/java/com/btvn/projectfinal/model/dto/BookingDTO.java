package com.btvn.projectfinal.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {

    @NotNull(message = "Vui lòng chọn giảng viên")
    private Long lecturerId;

    @NotNull(message = "Vui lòng chọn ngày giờ tư vấn")
    @Future(message = "Thời gian đặt lịch phải ở tương lai")
    private LocalDateTime scheduledTime;

    @Size(max = 500, message = "Ghi chú không vượt quá 500 ký tự")
    private String note;
}
