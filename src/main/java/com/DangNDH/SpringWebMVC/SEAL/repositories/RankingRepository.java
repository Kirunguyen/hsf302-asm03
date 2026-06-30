package com.DangNDH.SpringWebMVC.SEAL.repositories;

import com.DangNDH.SpringWebMVC.SEAL.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, UUID> {

  List<Ranking> findByTeamEventId(UUID eventId);
}
