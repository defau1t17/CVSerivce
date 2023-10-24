package com.example.cvservice.Repository;

import com.example.cvservice.Entity.Main.CandidatesTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateTestsRepository extends JpaRepository<CandidatesTest,Long> {
}
