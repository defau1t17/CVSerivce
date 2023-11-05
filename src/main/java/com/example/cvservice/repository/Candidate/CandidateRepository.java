package com.example.cvservice.repository.Candidate;

import com.example.cvservice.entity.main.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAll(Specification<Candidate> candidateSpecification, Pageable pageable);

}
