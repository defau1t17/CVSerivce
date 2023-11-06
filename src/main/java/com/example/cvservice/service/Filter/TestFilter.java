package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.TestFilterDTO;
import com.example.cvservice.entity.Specialization;
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
            if (testFilterDTO.getSpecializationNames() != null && !testFilterDTO.getSpecializationNames().isEmpty()) {
                Join<Test, Specialization> specJoin = root.join("specializations");
                List<String> specializationNames = testFilterDTO.getSpecializationNames();
                predicates.add(specJoin.get("name").in(specializationNames));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static TestFilterDTO generateFilterFromParams(String name, String description, List<String> specNames) {
        TestFilterDTO testFilterDTO = new TestFilterDTO();
        if (name != null && !name.trim().isEmpty()) {
            testFilterDTO.setName(name.trim());
        }
        if (description != null && !description.trim().isEmpty()) {
            testFilterDTO.setDescription(description.trim());
        }
        if (specNames != null && !specNames.isEmpty()) {
            testFilterDTO.setSpecializationNames(specNames);
        }
        return testFilterDTO;
    }
}
