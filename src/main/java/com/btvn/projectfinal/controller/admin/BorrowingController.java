package com.btvn.projectfinal.controller.admin;

import com.btvn.projectfinal.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/borrowing")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("records", borrowingService.getPendingRecords());
        return "admin/borrowing-list";
    }

    @PostMapping("/confirm/{id}")
    public String confirm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            borrowingService.confirmIssue(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xuất kho thành công!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/borrowing";
    }
}
