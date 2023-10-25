package com.example.cvservice.Service.Candidate;

import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Repository.Candidate.CandidateRepository;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateService implements EntityOperations {


    @Autowired
    private CandidateRepository repository;

    @Override
    public void save(Object object) {
        repository.save((Candidate) object);
    }

    @Override
    public void update(Object object) {
        repository.save((Candidate) object);
    }

    @Override
    public void delete(Object object) {
        repository.delete((Candidate) object);
    }


}
