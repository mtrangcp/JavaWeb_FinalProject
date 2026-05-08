package com.btvn.projectfinal.controller.student;

import com.btvn.projectfinal.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public String history(Authentication authentication, Model model) {
        model.addAttribute("histories",
                historyService.getHistory(authentication.getName()));
        return "student/history";
    }
}