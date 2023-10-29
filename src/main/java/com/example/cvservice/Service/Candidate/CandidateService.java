package com.example.cvservice.Service.Candidate;

import com.example.cvservice.DTO.Candidate.UpdateCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Repository.Candidate.CandidateRepository;
import com.example.cvservice.Service.EntityOperations;
import com.example.cvservice.Service.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService implements EntityOperations {


    @Autowired
    private CandidateRepository repository;


    public List<Candidate> findAllCandidates() {
        return repository.findAll();
    }

    public Page<Candidate> findAllCandidatesByPageNumber(int pageNumber, int pageSize, List<String> sort) {
        Pageable pageable = null;
        if (sort != null) {
            System.out.println(sort.stream().collect(Collectors.joining(",")));


            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(new SortedList().getAscendingSortedList(sort)));
//            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort.s).reverse());


        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return repository.findAll(pageable);
    }


    public Optional<Candidate> findCandidateByID(Long id) {
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

    @Transactional

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
        Optional<Candidate> clientById = findCandidateByID(id);
        if (clientById.isPresent()) {
            Candidate candidate = new UpdateCandidateData().updateCandidate(clientById.get(), updateCandidateDTO);
            repository.save(candidate);
        }
    }


}
