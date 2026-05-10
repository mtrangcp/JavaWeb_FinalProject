package com.btvn.projectfinal.controller.lecturer;

import com.btvn.projectfinal.model.dto.EvaluationDTO;
import com.btvn.projectfinal.model.entity.MentoringSession;
import com.btvn.projectfinal.repository.EquipmentRepository;
import com.btvn.projectfinal.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/lecturer/sessions")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final EquipmentRepository equipmentRepository;

    @GetMapping
    public String sessionList(Authentication authentication, Model model) {
        List<MentoringSession> sessions = evaluationService.getPendingSessions(authentication.getName());
        System.out.println("Sessions count: " + sessions.size());
        sessions.forEach(s -> System.out.println("Session id: " + s.getId()));
        model.addAttribute("sessions", sessions);
        return "lecturer/sessions";
    }

    @GetMapping("/evaluate/{sessionId}")
    public String evaluateForm(@PathVariable Long sessionId, Model model) {
        EvaluationDTO dto = new EvaluationDTO();
        dto.setSessionId(sessionId);

        model.addAttribute("evaluationDTO", dto);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("equipments", equipmentRepository.findAll());
        return "lecturer/evaluation-form";
    }

    @PostMapping("/evaluate")
    public String evaluate(
            @Valid @ModelAttribute("evaluationDTO") EvaluationDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("equipments", equipmentRepository.findAll());
            return "lecturer/evaluation-form";
        }

        try {
            evaluationService.evaluate(dto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Lưu đánh giá thành công!");
            return "redirect:/lecturer/sessions";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("equipments", equipmentRepository.findAll());
            return "lecturer/evaluation-form";
        }
    }
}