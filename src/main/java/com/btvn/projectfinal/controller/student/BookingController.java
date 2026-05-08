package com.btvn.projectfinal.controller.student;

import com.btvn.projectfinal.model.dto.BookingDTO;
import com.btvn.projectfinal.repository.DepartmentRepository;
import com.btvn.projectfinal.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public String bookingPage(Model model) {
        model.addAttribute("bookingDTO", new BookingDTO());
        model.addAttribute("departments", departmentRepository.findAll());
        return "student/booking";
    }

    @GetMapping("/lecturers")
    @ResponseBody
    public List<Map<String, Object>> getLecturers(@RequestParam Long departmentId) {
        return bookingService.getLecturersByDepartment(departmentId).stream()
                .map(l -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", l.getId());
                    map.put("name", l.getFullName());
                    return map;
                })
                .toList();
    }

    @PostMapping
    public String book(
            Authentication authentication,
            @Valid @ModelAttribute("bookingDTO") BookingDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "student/booking";
        }

        try {
            bookingService.book(authentication.getName(), dto);
            redirectAttributes.addFlashAttribute("successMessage", "Đặt lịch thành công!");
            return "redirect:/student/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("departments", departmentRepository.findAll());
            return "student/booking";
        }
    }
}