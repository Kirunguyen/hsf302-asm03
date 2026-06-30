package com.DangNDH.SpringWebMVC.SEAL.services;

import com.DangNDH.SpringWebMVC.SEAL.entities.Award;
import com.DangNDH.SpringWebMVC.SEAL.repositories.AwardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AwardService {

  private final AwardRepository awardRepository;

  public AwardService(AwardRepository awardRepository) {
    this.awardRepository = awardRepository;
  }

  public List<Award> findAll() {
    return awardRepository.findAll();
  }

  public Award findById(UUID id) {
    return awardRepository.findById(id).orElse(null);
  }

  public void save(Award award) {
    if (award.getAnnouncedAt() == null) {
      award.setAnnouncedAt(LocalDateTime.now());
    }
    awardRepository.save(award);
  }

  public void delete(UUID id) {
    awardRepository.deleteById(id);
  }
}
