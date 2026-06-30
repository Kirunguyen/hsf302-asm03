package com.DangNDH.SpringWebMVC.SEAL.repositories;

import com.DangNDH.SpringWebMVC.SEAL.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AwardRepository extends JpaRepository<Award, UUID> {

  List<Award> findByEventId(UUID eventId);
}
