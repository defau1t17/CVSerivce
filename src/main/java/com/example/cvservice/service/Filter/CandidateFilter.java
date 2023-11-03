package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.Candidate.CandidateFilterDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Direction.DirectionService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CandidateFilter {

    public static Specification<Candidate> filterCandidateByParams(CandidateFilterDTO candidateFilter, DirectionService service) {
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
            if (candidateFilter.getDirectionNames() != null && !candidateFilter.getDirectionNames().isEmpty()) {
                Join<Candidate, Direction> directionJoin = root.join("directions");
                List<String> directionNames = candidateFilter.getDirectionNames();
                predicates.add(directionJoin.get("name").in(directionNames));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static CandidateFilterDTO generateCandidateFromParams(String name, String secondName, String patr, List<String> dirNames) {
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
        if (dirNames != null && !dirNames.isEmpty()) {
            candidateFilter.setDirectionNames(dirNames);
        }
        return candidateFilter;
    }


}
