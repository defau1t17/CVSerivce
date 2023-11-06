package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.CandidateFilterDTO;
import com.example.cvservice.entity.Candidate;
import com.example.cvservice.entity.Specialization;
import com.example.cvservice.service.SpecializationService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CandidateFilter {

    public Specification<Candidate> filterCandidateByParams(CandidateFilterDTO candidateFilter, SpecializationService service) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (candidateFilter.getName() != null && !candidateFilter.getName().isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + candidateFilter.getName() + "%"));
            }
            if (candidateFilter.getSecond_name() != null && !candidateFilter.getSecond_name().isEmpty()) {
                predicates.add(builder.like(root.get("secondName"), "%" + candidateFilter.getSecond_name() + "%"));
            }
            if (candidateFilter.getPatr() != null && !candidateFilter.getPatr().isEmpty()) {
                predicates.add(builder.like(root.get("patronymic"), "%" + candidateFilter.getPatr() + "%"));
            }
            if (candidateFilter.getSpecializationNames() != null && !candidateFilter.getSpecializationNames().isEmpty()) {
                Join<Candidate, Specialization> specificationJoin = root.join("specializations");
                List<String> specializationNames = candidateFilter.getSpecializationNames();
                predicates.add(specificationJoin.get("name").in(specializationNames));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public CandidateFilterDTO generateCandidateFromParams(String name, String secondName, String patr, List<String> spec) {
        CandidateFilterDTO candidateFilter = new CandidateFilterDTO();
        if (name != null && !name.trim().isEmpty()) {
            candidateFilter.setName(name.trim());
        }
        if (secondName != null && !secondName.trim().isEmpty()) {
            candidateFilter.setSecond_name(secondName.trim());
        }
        if (patr != null && !patr.trim().isEmpty()) {
            candidateFilter.setPatr(patr.trim());
        }
        if (spec != null && !spec.isEmpty()) {
            candidateFilter.setSpecializationNames(spec);
        }
        return candidateFilter;
    }


}
