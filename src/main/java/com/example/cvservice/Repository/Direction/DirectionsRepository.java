package com.example.cvservice.Repository.Direction;

import com.example.cvservice.Entity.Main.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionsRepository extends JpaRepository<Direction, Long> {
}
