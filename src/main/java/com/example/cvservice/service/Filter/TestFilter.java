package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.TestFilterDTO;
import com.example.cvservice.entity.Direction;
import com.example.cvservice.entity.Test;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TestFilter {

    public static Specification<Test> filterTests(TestFilterDTO testFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (testFilterDTO.getName() != null && !testFilterDTO.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + testFilterDTO.getName().trim() + "%"));
            }
            if (testFilterDTO.getDescription() != null && !testFilterDTO.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + testFilterDTO.getDescription().trim() + "%"));
            }
            if (testFilterDTO.getDirectionNames() != null && !testFilterDTO.getDirectionNames().isEmpty()) {
                Join<Test, Direction> directionJoin = root.join("directions");
                List<String> directionNames = testFilterDTO.getDirectionNames();
                predicates.add(directionJoin.get("name").in(directionNames));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static TestFilterDTO generateFilterFromParams(String name, String description, List<String> directionNames) {
        TestFilterDTO testFilterDTO = new TestFilterDTO();
        if (name != null && !name.trim().isEmpty()) {
            testFilterDTO.setName(name.trim());
        }
        if (description != null && !description.trim().isEmpty()) {
            testFilterDTO.setDescription(description.trim());
        }
        if (directionNames != null && !directionNames.isEmpty()) {
            testFilterDTO.setDirectionNames(directionNames);
        }
        return testFilterDTO;
    }
}
