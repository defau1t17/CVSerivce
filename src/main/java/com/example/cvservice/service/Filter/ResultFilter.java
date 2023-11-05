package com.example.cvservice.service.Filter;

import com.example.cvservice.dto.Result.ResultFilterDTO;
import com.example.cvservice.entity.main.Result;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.entity.main.Test;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResultFilter {
    public Specification<Result> withParameters(ResultFilterDTO resultFilterDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (resultFilterDTO.getCandidateFilterDTO().getName() != null && StringUtils.hasText(resultFilterDTO.getCandidateFilterDTO().getName().trim())) {
                predicates.add(builder.like(root.get("candidate").get("name"), "%" + resultFilterDTO.getCandidateFilterDTO().getName() + "%"));
            }
            if (resultFilterDTO.getCandidateFilterDTO().getSecond_name() != null && StringUtils.hasText(resultFilterDTO.getCandidateFilterDTO().getSecond_name().trim())) {
                predicates.add(builder.like(root.get("candidate").get("secondName"), "%" + resultFilterDTO.getCandidateFilterDTO().getSecond_name() + "%"));
            }
            if (resultFilterDTO.getCandidateFilterDTO().getPatr() != null && StringUtils.hasText(resultFilterDTO.getCandidateFilterDTO().getPatr().trim())) {
                predicates.add(builder.like(root.get("candidate").get("patronymic"), "%" + resultFilterDTO.getCandidateFilterDTO().getPatr() + "%"));
            }
            if (resultFilterDTO.getTestFilterDTO().getName() != null && StringUtils.hasText(resultFilterDTO.getTestFilterDTO().getName().trim())) {
                predicates.add(builder.like(root.get("test").get("name"), "%" + resultFilterDTO.getTestFilterDTO().getName() + "%"));
            }
            if (resultFilterDTO.getTestFilterDTO().getDescription() != null && StringUtils.hasText(resultFilterDTO.getTestFilterDTO().getDescription().trim())) {
                predicates.add(builder.like(root.get("test").get("description"), "%" + resultFilterDTO.getTestFilterDTO().getDescription() + "%"));
            }
            if (resultFilterDTO.getTestFilterDTO().getDirectionNames() != null && !resultFilterDTO.getTestFilterDTO().getDirectionNames().isEmpty()) {
                Join<Result, Test> testJoin = root.join("test");
                Join<Test, Direction> directionJoin = testJoin.join("directions");
                predicates.add(directionJoin.get("name").in(resultFilterDTO.getTestFilterDTO().getDirectionNames()));
            }
            if (resultFilterDTO.getFromDate() != null && resultFilterDTO.getToDate() != null) {
                predicates.add(builder.between(root.get("grade").get("date"), resultFilterDTO.getFromDate(), resultFilterDTO.getToDate()));
            } else if (resultFilterDTO.getFromDate() == null && resultFilterDTO.getToDate() != null) {
                predicates.add(builder.between(root.get("grade").get("date"), LocalDate.now(), resultFilterDTO.getToDate()));
            } else if (resultFilterDTO.getFromDate() != null && resultFilterDTO.getToDate() == null) {
                predicates.add(builder.between(root.get("grade").get("date"), resultFilterDTO.getFromDate(), LocalDate.now()));
            }
            if (resultFilterDTO.getFromMark() >= 0 && resultFilterDTO.getFromMark() <= 100 && resultFilterDTO.getToMark() >= 0 && resultFilterDTO.getFromMark() <= resultFilterDTO.getToMark()) {
                predicates.add(builder.between(root.get("grade").get("mark"), resultFilterDTO.getFromMark(), resultFilterDTO.getToMark()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public ResultFilterDTO generateResultFilterDTOFromParams(String candidateName, String candidateSecondName, String candidatePatr,
                                                                String testName, String testDesc, List<String> directionNames,
                                                                LocalDate fromDate, LocalDate toDate, int fromMark, int toMark) {
        ResultFilterDTO resultFilterDTO = new ResultFilterDTO();
        resultFilterDTO.setCandidateFilterDTO(new CandidateFilter().generateCandidateFromParams(candidateName, candidateSecondName, candidatePatr, null));
        resultFilterDTO.setTestFilterDTO(TestFilter.generateFilterFromParams(testName, testDesc, directionNames));

        if (fromDate != null) {
            resultFilterDTO.setFromDate(fromDate);
        }
        if (toDate != null) {
            resultFilterDTO.setToDate(toDate);
        }
        if (fromMark >= 0 && toMark >= 0 && toMark <= 100 && fromMark < toMark) {
            resultFilterDTO.setFromMark(fromMark);
            resultFilterDTO.setToMark(toMark);
        }
        return resultFilterDTO;
    }
}
