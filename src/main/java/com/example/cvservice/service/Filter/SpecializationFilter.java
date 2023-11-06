package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.SpecializationFilterDTO;
import com.example.cvservice.entity.Specialization;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecializationFilter {

    public  Specification<Specialization> filterSpecialization(SpecializationFilterDTO specializationFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (specializationFilterDTO.getName() != null && !specializationFilterDTO.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + specializationFilterDTO.getName().trim() + "%"));
            }
            if (specializationFilterDTO.getDescription() != null && !specializationFilterDTO.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + specializationFilterDTO.getDescription().trim() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public SpecializationFilterDTO generateSpecializationFilterFromParams(String name, String description) {
        SpecializationFilterDTO specializationFilterDTO = new SpecializationFilterDTO();
        if (name != null && !name.trim().isEmpty()) {
            specializationFilterDTO.setName(name);
        }

        if (description != null && !description.trim().isEmpty()) {
            specializationFilterDTO.setDescription(description);
        }
        return specializationFilterDTO;
    }
}
