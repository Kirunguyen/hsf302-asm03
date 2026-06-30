package com.DangNDH.SpringWebMVC.SEAL.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "rankings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ranking {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "round_id", nullable = false)
  private UUID roundId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @Column(name = "total_score")
  private Double totalScore;

  @Column(name = "rank_position")
  private Integer rankPosition;

  @Column(name = "advancement_status")
  private String advancementStatus;

  @Column(name = "eliminated_reason", columnDefinition = "NVARCHAR(MAX)")
  private String eliminatedReason;
}
