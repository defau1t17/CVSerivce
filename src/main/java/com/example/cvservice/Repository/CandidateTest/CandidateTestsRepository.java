package com.example.cvservice.Repository.CandidateTest;

import com.example.cvservice.Entity.Grade.TestGrade;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.CandidatesTest;
import com.example.cvservice.Entity.Main.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateTestsRepository extends JpaRepository<CandidatesTest, Long> {

    Optional<CandidatesTest> findFirstByCandidateAndTestAndGrade(Candidate candidate, Test test, TestGrade grade);
    List<CandidatesTest> findAllByCandidate(Candidate candidate);

}
