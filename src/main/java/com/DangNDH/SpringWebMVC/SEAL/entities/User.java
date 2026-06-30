package com.DangNDH.SpringWebMVC.SEAL.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private String role;
  private String university;

  @Column(name = "student_code")
  private String studentCode;

  private String status;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;
}
