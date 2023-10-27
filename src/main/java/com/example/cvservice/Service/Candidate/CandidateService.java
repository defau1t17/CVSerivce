package com.example.cvservice.Service.Candidate;

import com.example.cvservice.DTO.Candidate.UpdateCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Repository.Candidate.CandidateRepository;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService implements EntityOperations {


    @Autowired
    private CandidateRepository repository;


    //нужно переделать для пагинации
    public List<Candidate> findAllCandidates() {
        return repository.findAll();
    }

    public Optional<Candidate> findClientById(Long id) {
        return repository.findById(id);
    }

    public void deleteByID(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
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

    @Transactional
    public void updateByID(Long id, UpdateCandidateDTO updateCandidateDTO) {
        Optional<Candidate> clientById = findClientById(id);
        if (clientById.isPresent()) {
            Candidate candidate = new UpdateCandidateData().updateCandidate(clientById.get(), updateCandidateDTO);
            repository.save(candidate);
        }
    }


}
