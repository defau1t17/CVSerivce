package com.example.cvservice.Service.Candidate;

import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Filter.CandidateFilter;
import com.example.cvservice.Repository.Candidate.CandidateRepository;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CandidateService implements EntityOperations {


    @Autowired
    private CandidateRepository repository;


    @Autowired
    private DirectionService directionService;

    public List<Candidate> findAllCandidates() {
        return repository.findAll();
    }

    public Page<Candidate> findAllCandidatesByPageNumber(int pageNumber, int pageSize, String param, String direction, boolean filer, String name, String secondName, String patr, List<String> directionsNames) {
        Pageable pageable = null;
        Specification<Candidate> candidateSpecification = null;

        if (param != null && direction != null && !filer) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction), param));
        } else if (param != null && direction != null && filer) {
            candidateSpecification = CandidateFilter.filterCandidateByParams(CandidateFilter.generateCandidateFromParams(name, secondName, patr, directionsNames), directionService);
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction), param));
            try {
                return repository.findAll(candidateSpecification, pageable);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (filer) {
            candidateSpecification = CandidateFilter.filterCandidateByParams(CandidateFilter.generateCandidateFromParams(name, secondName, patr, directionsNames), directionService);
            pageable = PageRequest.of(pageNumber, pageSize);
            return repository.findAll(candidateSpecification, pageable);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return repository.findAll(candidateSpecification, pageable);
    }


    public Optional<Candidate> findCandidateByID(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Object object) {
        repository.save((Candidate) object);
    }

    @Transactional

    @Override
    public void update(Object object) {
        repository.save((Candidate) object);
    }

    @Override
    public void delete(Object object) {
        repository.delete((Candidate) object);
    }


}
