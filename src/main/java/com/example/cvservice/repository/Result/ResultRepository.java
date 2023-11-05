package com.example.cvservice.repository.Result;

import com.example.cvservice.entity.grade.TestGrade;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Result;
import com.example.cvservice.entity.main.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findFirstByCandidateAndTestAndGrade(Candidate candidate, Test test, TestGrade grade);

    List<Result> findAllByCandidate(Candidate candidate);

    Page<Result> findAll(Specification<Result> specification, Pageable pageable);


}
