package com.DangNDH.SpringWebMVC.SEAL.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Column(name = "category_id")
  private UUID categoryId;

  private String name;

  @Column(name = "approval_status")
  private String approvalStatus;

  @Column(name = "registration_status")
  private String registrationStatus;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;
}
