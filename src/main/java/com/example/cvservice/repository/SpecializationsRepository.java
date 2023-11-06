package com.example.cvservice.repository;

import com.example.cvservice.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationsRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);

    List<Specialization> findAllByNameIn(List<String> names);

    Page<Specialization> findAll(Specification<Specialization> specification, Pageable pageable);



}
