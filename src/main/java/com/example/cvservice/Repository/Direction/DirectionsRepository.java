package com.example.cvservice.Repository.Direction;

import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Entity.Main.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectionsRepository extends JpaRepository<Direction, Long> {
    Optional<Direction> findByName(String name);

    List<Direction> findAllByNameIn(List<String> names);

    Page<Direction> findAll(Specification<Direction> specification, Pageable pageable);


}
