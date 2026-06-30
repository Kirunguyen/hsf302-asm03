package com.DangNDH.SpringWebMVC.SEAL.controllers;

import com.DangNDH.SpringWebMVC.SEAL.entities.Award;
import com.DangNDH.SpringWebMVC.SEAL.repositories.EventRepository;
import com.DangNDH.SpringWebMVC.SEAL.repositories.TeamRepository;
import com.DangNDH.SpringWebMVC.SEAL.services.AwardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/awards")
public class AwardController {

  private final AwardService awardService;
  private final EventRepository eventRepository;
  private final TeamRepository teamRepository;

  public AwardController(AwardService awardService, EventRepository eventRepository, TeamRepository teamRepository) {
    this.awardService = awardService;
    this.eventRepository = eventRepository;
    this.teamRepository = teamRepository;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("awards", awardService.findAll());
    return "awards/list";
  }

  @GetMapping("/new")
  public String showForm(Model model) {
    model.addAttribute("award", new Award());
    model.addAttribute("events", eventRepository.findAll());
    model.addAttribute("teams", teamRepository.findAll());
    return "awards/form";
  }

  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable UUID id, Model model) {
    Award award = awardService.findById(id);
    if (award == null) {
      return "redirect:/awards";
    }
    model.addAttribute("award", award);
    model.addAttribute("events", eventRepository.findAll());
    model.addAttribute("teams", teamRepository.findAll());
    return "awards/form";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute Award award) {
    awardService.save(award);
    return "redirect:/awards";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable UUID id) {
    awardService.delete(id);
    return "redirect:/awards";
  }
}
