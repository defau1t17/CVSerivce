package com.example.cvservice.Repository.Candidate;

import com.example.cvservice.Entity.Main.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAll(Specification<Candidate> candidateSpecification, Pageable pageable);

//    Page<Candidate> findAll(Specification<Candidate> candidateSpecification);

//    Page<Candidate> findAll(Specification<Candidate> candidateSpecification);

}
