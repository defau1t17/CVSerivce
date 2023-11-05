package com.example.cvservice.service.Result;

import com.example.cvservice.controller.view.Candidate.CandidatesController;
import com.example.cvservice.dto.Result.ResultDTO;
import com.example.cvservice.dto.Result.UpdateResultDTO;
import com.example.cvservice.entity.grade.TestGrade;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Result;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Filter.ResultFilter;
import com.example.cvservice.repository.Result.ResultRepository;
import com.example.cvservice.service.EntityOperations;
import com.example.cvservice.service.Test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService implements EntityOperations {

    @Autowired
    private ResultRepository repository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;

    Logger logger = LoggerFactory.getLogger(ResultService.class);


    public List<Result> findAll() {
        return repository.findAll();
    }

    public List<Result> findAllResultsByCandidate(Candidate candidate) {
        return repository.findAllByCandidate(candidate);
    }

    public Optional<Result> findResultByID(Long id) {
        return repository.findById(id);
    }

    public Page<Result> findResultsByParams(int page, int size, String candidateName, String candidateSecondName, String candidatePatronymic,
                                            String testName, String testDesc, List<String> directionNames, LocalDate fromDate, LocalDate toDate,
                                            int fromMark, int toMark, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        logger.info("page with result's has been created");
        return repository.findAll(new ResultFilter().withParameters(new ResultFilter().generateResultFilterDTOFromParams(candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, directionNames, fromDate, toDate, fromMark, toMark)), pageable);
    }

    @Transactional
    @Override
    public void save(Object object) {
        repository.save((Result) object);
    }

    @Transactional
    @Override
    public void update(Object object) {
        repository.save((Result) object);
    }


    public Optional<Result> isResultExists(Result result) {
        return repository.findFirstByCandidateAndTestAndGrade(result.getCandidate(), result.getTest(), result.getGrade());
    }

    public Result saveNewResult(ResultDTO resultDTO) {
        logger.info("new result incoming");
        if (resultDTO.isValid(candidateService, testService)) {
            logger.info("new result valid");
            Candidate candidate = candidateService.findCandidateByID(resultDTO.getCandidateID()).get();
            Test test = testService.findTestByID(resultDTO.getTestID()).get();
            TestGrade grade = TestGrade.builder().date(resultDTO.getDate()).mark(resultDTO.getMark()).build();
            Result newResult = Result.builder().
                    candidate(candidate)
                    .test(test)
                    .grade(grade)
                    .build();
            if (!isResultExists(newResult).isPresent()) {
                logger.info("new result doesen't exists");
                save(newResult);
                logger.info("new result created");
                return newResult;
            } else {
                logger.warn("new result has been rejected");
                throw new ObjectAlreadyExistsException("Result", "");
            }


        } else throw new ObjectIsEmptyException();
    }

    public Result updateResult(Long resultID, UpdateResultDTO updateResultDTO) {
        logger.info("updated result incoming");
        if (updateResultDTO.isValid(candidateService, testService)) {
            logger.info("updated result valid");
            if (findResultByID(resultID).isPresent()) {
                logger.info("updated result exists");
                Result updatedResult = findResultByID(resultID).get().update(updateResultDTO);
                save(updatedResult);
                logger.info("result updated");
                return updatedResult;
            } else {
                logger.info("updated result has been rejected");
                throw new ObjectNotFoundException("Result", resultID);
            }
        } else throw new ObjectIsEmptyException();
    }


}


