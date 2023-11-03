package com.example.cvservice.service.CandidateTest;

import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.CandidatesTest;
import com.example.cvservice.service.Filter.CandiTestFilter;
import com.example.cvservice.repository.CandidateTest.CandidateTestsRepository;
import com.example.cvservice.service.EntityOperations;
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
public class CandidateTestsService implements EntityOperations {

    @Autowired
    private CandidateTestsRepository repository;

    public List<CandidatesTest> findAll() {
        return repository.findAll();
    }

    public List<CandidatesTest> findAllTestsByCandidate(Candidate candidate) {
        return repository.findAllByCandidate(candidate);
    }

    public Optional<CandidatesTest> findCandidateTestByID(Long id) {
        return repository.findById(id);
    }

    public Page<CandidatesTest> findCandidatesTestsByParams(int page, int size, String candidateName, String candidateSecondName, String candidatePatronymic,
                                                            String testName, String testDesc, List<String> directionNames, LocalDate fromDate, LocalDate toDate,
                                                            int fromMark, int toMark, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        return repository.findAll(new CandiTestFilter().withParameters(new CandiTestFilter().generateCandidateFilterDTOFromParams(candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, directionNames, fromDate, toDate, fromMark, toMark)), pageable);
    }

    @Transactional
    @Override
    public void save(Object object) {
        repository.save((CandidatesTest) object);
    }

    @Transactional
    @Override
    public void update(Object object) {
        repository.save((CandidatesTest) object);
    }

    @Override
    public void delete(Object object) {
        repository.delete((CandidatesTest) object);
    }

    public Optional<CandidatesTest> isCandiTestsExists(CandidatesTest candidatesTest) {
        return repository.findFirstByCandidateAndTestAndGrade(candidatesTest.getCandidate(), candidatesTest.getTest(), candidatesTest.getGrade());
    }

}
