package com.example.cvservice.Filter;

import com.example.cvservice.DAO.CandidateDAO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Direction.DirectionService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CandidateFilter {

    public static Specification<Candidate> filterCandidateByParams(CandidateDAO candidateDAO, DirectionService service) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (candidateDAO.getName() != null && !candidateDAO.getName().isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + candidateDAO.getName() + "%"));
            }
            if (candidateDAO.getSecond_name() != null && !candidateDAO.getSecond_name().isEmpty()) {
                predicates.add(builder.like(root.get("secondName"), "%" + candidateDAO.getSecond_name() + "%"));
            }
            if (candidateDAO.getPatr() != null && !candidateDAO.getPatr().isEmpty()) {
                predicates.add(builder.like(root.get("patronymic"), "%" + candidateDAO.getPatr() + "%"));
            }
            if (candidateDAO.getDirectionNames() != null && !candidateDAO.getDirectionNames().isEmpty()) {
                Join<Candidate, Direction> directionJoin = root.join("directions");
                List<String> directionNames = candidateDAO.getDirectionNames();
                predicates.add(directionJoin.get("name").in(directionNames));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static CandidateDAO generateCandidateFromParams(String name, String secondName, String patr, List<String> dirNames) {
        CandidateDAO candidateDAO = new CandidateDAO();
        if (name != null && !name.trim().isEmpty()) {
            candidateDAO.setName(name);
        }

        if (secondName != null && !secondName.trim().isEmpty()) {
            candidateDAO.setSecond_name(secondName);
        }
        if (patr != null && !patr.trim().isEmpty()) {
            candidateDAO.setPatr(patr);
        }
        if (dirNames != null && !dirNames.isEmpty()) {
            candidateDAO.setDirectionNames(dirNames);
        }
        return candidateDAO;
    }


}
