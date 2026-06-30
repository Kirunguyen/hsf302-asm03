package com.DangNDH.SpringWebMVC.SEAL.controllers;

import com.DangNDH.SpringWebMVC.SEAL.entities.Report;
import com.DangNDH.SpringWebMVC.SEAL.repositories.EventRepository;
import com.DangNDH.SpringWebMVC.SEAL.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;
  private final EventRepository eventRepository;

  public ReportController(ReportService reportService, EventRepository eventRepository) {
    this.reportService = reportService;
    this.eventRepository = eventRepository;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("reports", reportService.findAll());
    model.addAttribute("events", eventRepository.findAll());
    return "reports/list";
  }

  @PostMapping("/generate")
  public String generate(@RequestParam UUID eventId, @RequestParam String reportType) {
    reportService.generateReport(eventId, reportType);
    return "redirect:/reports";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable UUID id, Model model) {
    Report report = reportService.findById(id);
    if (report == null) {
      return "redirect:/reports";
    }
    model.addAttribute("report", report);
    return "reports/detail";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable UUID id) {
    reportService.delete(id);
    return "redirect:/reports";
  }
}
