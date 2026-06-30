package com.DangNDH.SpringWebMVC.SEAL.services;

import com.DangNDH.SpringWebMVC.SEAL.entities.*;
import com.DangNDH.SpringWebMVC.SEAL.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReportService {

  private final ReportRepository reportRepository;
  private final EventRepository eventRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final RankingRepository rankingRepository;
  private final AwardRepository awardRepository;
  private final UserRepository userRepository;

  public ReportService(ReportRepository reportRepository, EventRepository eventRepository,
      TeamRepository teamRepository, TeamMemberRepository teamMemberRepository,
      RankingRepository rankingRepository, AwardRepository awardRepository,
      UserRepository userRepository) {
    this.reportRepository = reportRepository;
    this.eventRepository = eventRepository;
    this.teamRepository = teamRepository;
    this.teamMemberRepository = teamMemberRepository;
    this.rankingRepository = rankingRepository;
    this.awardRepository = awardRepository;
    this.userRepository = userRepository;
  }

  public List<Report> findAll() {
    return reportRepository.findAll();
  }

  public Report findById(UUID id) {
    return reportRepository.findById(id).orElse(null);
  }

  public void delete(UUID id) {
    reportRepository.deleteById(id);
  }

  public void generateReport(UUID eventId, String reportType) {
    Event event = eventRepository.findById(eventId).orElse(null);
    if (event == null)
      return;

    List<Team> teams = teamRepository.findByEventId(eventId);
    List<TeamMember> members = teamMemberRepository.findByTeamEventId(eventId);
    List<Ranking> rankings = rankingRepository.findByTeamEventId(eventId);
    List<Award> awards = awardRepository.findByEventId(eventId);

    User systemUser = userRepository.findAll().stream().findFirst().orElseGet(() -> {
      User mockUser = new User();
      mockUser.setFullName("System Admin");
      mockUser.setEmail("admin@seal.univ");
      mockUser.setPassword("SystemPass");
      mockUser.setRole("organizer");
      return userRepository.save(mockUser);
    });

    StringBuilder jsonText = new StringBuilder();
    jsonText.append("{\n");
    jsonText.append("  \"event\": \"").append(event.getName()).append("\",\n");
    jsonText.append("  \"status\": \"").append(event.getStatus() != null ? event.getStatus() : "N/A").append("\",\n");
    jsonText.append("  \"totalTeams\": ").append(teams.size()).append(",\n");
    jsonText.append("  \"totalMembers\": ").append(members.size()).append(",\n");
    jsonText.append("  \"rankings\": [\n");
    for (int i = 0; i < rankings.size(); i++) {
      Ranking r = rankings.get(i);
      jsonText.append("    {")
          .append("\"team\": \"").append(r.getTeam().getName()).append("\", ")
          .append("\"score\": ").append(r.getTotalScore()).append(", ")
          .append("\"rank\": ").append(r.getRankPosition())
          .append("}");
      if (i < rankings.size() - 1)
        jsonText.append(",\n");
    }
    jsonText.append("\n  ],\n");
    jsonText.append("  \"awards\": [\n");
    for (int i = 0; i < awards.size(); i++) {
      Award a = awards.get(i);
      jsonText.append("    {")
          .append("\"title\": \"").append(a.getTitle()).append("\", ")
          .append("\"winner\": \"").append(a.getTeam().getName()).append("\"")
          .append("}");
      if (i < awards.size() - 1)
        jsonText.append(",\n");
    }
    jsonText.append("\n  ],\n");
    jsonText.append("  \"generatedAt\": \"")
        .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\"\n");
    jsonText.append("}");

    Report report = new Report();
    report.setEvent(event);
    report.setGeneratedBy(systemUser);
    report.setReportType(reportType);
    report.setReportData(jsonText.toString());
    report.setCreatedAt(LocalDateTime.now());

    reportRepository.save(report);
  }
}
