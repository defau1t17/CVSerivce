package com.example.cvservice.repository.CandidateTest;

import com.example.cvservice.entity.grade.TestGrade;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.CandidatesTest;
import com.example.cvservice.entity.main.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateTestsRepository extends JpaRepository<CandidatesTest, Long> {

    Optional<CandidatesTest> findFirstByCandidateAndTestAndGrade(Candidate candidate, Test test, TestGrade grade);

    List<CandidatesTest> findAllByCandidate(Candidate candidate);

    Page<CandidatesTest> findAll(Specification<CandidatesTest> specification, Pageable pageable);


}
