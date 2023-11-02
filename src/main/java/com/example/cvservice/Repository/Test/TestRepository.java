package com.example.cvservice.Repository.Test;

import com.example.cvservice.Entity.Main.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByName(String name);

    Page<Test> findAll(Specification<Test> specification, Pageable pageable);

}
