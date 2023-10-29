package com.example.cvservice.Repository.Candidate;

import com.example.cvservice.Entity.Main.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

//    Page<Candidate> findAll(Pageable pageable,Sort sort);

//    Page<Candidate> findAll(Pageable pageable, Sort sort);

}
