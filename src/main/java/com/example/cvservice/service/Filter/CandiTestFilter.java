package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.Canditests.CandiTestFilterDTO;
import com.example.cvservice.entity.main.CandidatesTest;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.entity.main.Test;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandiTestFilter {
    public Specification<CandidatesTest> withParameters(CandiTestFilterDTO candiTestFilterDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (candiTestFilterDTO.getCandidateFilterDTO().getName() != null && StringUtils.hasText(candiTestFilterDTO.getCandidateFilterDTO().getName().trim())) {
                predicates.add(builder.like(root.get("candidate").get("name"), "%" + candiTestFilterDTO.getCandidateFilterDTO().getName() + "%"));
            }
            if (candiTestFilterDTO.getCandidateFilterDTO().getSecond_name() != null && StringUtils.hasText(candiTestFilterDTO.getCandidateFilterDTO().getSecond_name().trim())) {
                predicates.add(builder.like(root.get("candidate").get("secondName"), "%" + candiTestFilterDTO.getCandidateFilterDTO().getSecond_name() + "%"));
            }
            if (candiTestFilterDTO.getCandidateFilterDTO().getPatr() != null && StringUtils.hasText(candiTestFilterDTO.getCandidateFilterDTO().getPatr().trim())) {
                predicates.add(builder.like(root.get("candidate").get("patronymic"), "%" + candiTestFilterDTO.getCandidateFilterDTO().getPatr() + "%"));
            }
            if (candiTestFilterDTO.getTestFilterDTO().getName() != null && StringUtils.hasText(candiTestFilterDTO.getTestFilterDTO().getName().trim())) {
                predicates.add(builder.like(root.get("test").get("name"), "%" + candiTestFilterDTO.getTestFilterDTO().getName() + "%"));
            }
            if (candiTestFilterDTO.getTestFilterDTO().getDescription() != null && StringUtils.hasText(candiTestFilterDTO.getTestFilterDTO().getDescription().trim())) {
                predicates.add(builder.like(root.get("test").get("description"), "%" + candiTestFilterDTO.getTestFilterDTO().getDescription() + "%"));
            }
            if (candiTestFilterDTO.getTestFilterDTO().getDirectionNames() != null && !candiTestFilterDTO.getTestFilterDTO().getDirectionNames().isEmpty()) {
                Join<CandidatesTest, Test> testJoin = root.join("test");
                Join<Test, Direction> directionJoin = testJoin.join("directions");
                predicates.add(directionJoin.get("name").in(candiTestFilterDTO.getTestFilterDTO().getDirectionNames()));
            }
            if (candiTestFilterDTO.getFromDate() != null && candiTestFilterDTO.getToDate() != null) {
                predicates.add(builder.between(root.get("grade").get("date"), candiTestFilterDTO.getFromDate(), candiTestFilterDTO.getToDate()));
            } else if (candiTestFilterDTO.getFromDate() == null && candiTestFilterDTO.getToDate() != null) {
                predicates.add(builder.between(root.get("grade").get("date"), LocalDate.now(), candiTestFilterDTO.getToDate()));
            } else if (candiTestFilterDTO.getFromDate() != null && candiTestFilterDTO.getToDate() == null) {
                predicates.add(builder.between(root.get("grade").get("date"), candiTestFilterDTO.getFromDate(), LocalDate.now()));
            }
            if (candiTestFilterDTO.getFromMark() >= 0 && candiTestFilterDTO.getFromMark() <= 100 && candiTestFilterDTO.getToMark() >= 0 && candiTestFilterDTO.getFromMark() <= candiTestFilterDTO.getToMark()) {
                predicates.add(builder.between(root.get("grade").get("mark"), candiTestFilterDTO.getFromMark(), candiTestFilterDTO.getToMark()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public CandiTestFilterDTO generateCandidateFilterDTOFromParams(String candidateName, String candidateSecondName, String candidatePatr,
                                                                   String testName, String testDesc, List<String> directionNames,
                                                                   LocalDate fromDate, LocalDate toDate, int fromMark, int toMark) {
        CandiTestFilterDTO candiTestFilterDTO = new CandiTestFilterDTO();
        candiTestFilterDTO.setCandidateFilterDTO(CandidateFilter.generateCandidateFromParams(candidateName, candidateSecondName, candidatePatr, null));
        candiTestFilterDTO.setTestFilterDTO(TestFilter.generateFilterFromParams(testName, testDesc, directionNames));

        if (fromDate != null) {
            candiTestFilterDTO.setFromDate(fromDate);
        }
        if (toDate != null) {
            candiTestFilterDTO.setToDate(toDate);
        }
        if (fromMark >= 0 && toMark >= 0 && toMark <= 100 && fromMark < toMark) {
            candiTestFilterDTO.setFromMark(fromMark);
            candiTestFilterDTO.setToMark(toMark);
        }
        return candiTestFilterDTO;
    }
}
