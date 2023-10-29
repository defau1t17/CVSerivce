package com.example.cvservice.Service.CandidateTest;

import com.example.cvservice.Entity.Grade.TestGrade;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.CandidatesTest;
import com.example.cvservice.Entity.Main.Test;
import com.example.cvservice.Repository.CandidateTest.CandidateTestsRepository;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
