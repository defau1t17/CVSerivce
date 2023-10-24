package com.example.cvservice.Repository;

import com.example.cvservice.Entity.Main.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionsRepository extends JpaRepository<Direction, Long> {
}
