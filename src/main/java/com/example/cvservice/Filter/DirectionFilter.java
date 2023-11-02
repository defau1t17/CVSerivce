package com.example.cvservice.Filter;

import com.example.cvservice.DTO.Direction.DirectionFilterDTO;
import com.example.cvservice.DTO.Test.TestFilterDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Entity.Main.Test;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DirectionFilter {

    public static Specification<Direction> filterDirections(DirectionFilterDTO directionFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (directionFilterDTO.getName() != null && !directionFilterDTO.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + directionFilterDTO.getName().trim() + "%"));
            }
            if (directionFilterDTO.getDescription() != null && !directionFilterDTO.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + directionFilterDTO.getDescription().trim() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static DirectionFilterDTO generateDirectionFilterFromParams(String name, String description) {
        DirectionFilterDTO directionFilterDTO = new DirectionFilterDTO();
        if (name != null && !name.trim().isEmpty()) {
            directionFilterDTO.setName(name);
        }

        if (description != null && !description.trim().isEmpty()) {
            directionFilterDTO.setDescription(description);
        }
        return directionFilterDTO;
    }
}
