package com.DangNDH.SpringWebMVC.SEAL.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "generated_by")
  private User generatedBy;

  @Column(name = "report_type")
  private String reportType;

  @Column(name = "report_data", columnDefinition = "NVARCHAR(MAX)")
  private String reportData;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
