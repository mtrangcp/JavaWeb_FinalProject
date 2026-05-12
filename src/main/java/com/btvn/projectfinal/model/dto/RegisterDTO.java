package com.btvn.projectfinal.model.dto;

import com.btvn.projectfinal.model.entity.User;
import com.btvn.projectfinal.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class,
            message = "Tên đăng nhập không được để trống")
    @Size(groups = ValidationGroups.SizeGroup.class,
            min = 4, max = 50,
            message = "Tên đăng nhập từ 4-50 ký tự")
    @Pattern(groups = ValidationGroups.FormatGroup.class,
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Chỉ được dùng chữ, số và dấu _")
    private String username;

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class,
            message = "Mật khẩu không được để trống")
    @Size(groups = ValidationGroups.SizeGroup.class,
            min = 6,
            message = "Mật khẩu ít nhất 6 ký tự")
    private String password;

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class,
            message = "Vui lòng xác nhận mật khẩu")
    private String confirmPassword;

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class,
            message = "Họ và tên không được để trống")
    private String fullName;

    @NotNull(message = "Vui lòng chọn vai trò")
    private User.Role role;

    private String studentId;
    private Long departmentId;
}