package com.example.cvservice.service.Candidate;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.repository.Candidate.CandidateRepository;
import com.example.cvservice.service.Files.CVService;
import com.example.cvservice.service.Filter.CandidateFilter;
import com.example.cvservice.service.Direction.DirectionService;
import com.example.cvservice.service.EntityOperations;
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

    @Autowired
    private CVService cvService;

    public List<Candidate> findAllCandidates() {
        return repository.findAll();
    }

    public Page<Candidate> findAllCandidatesByPageNumber(int pageNumber, int pageSize, String param, String direction, String name, String secondName, String patr, List<String> directionsNames) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction), param));
        Specification<Candidate> candidateSpecification = new CandidateFilter().filterCandidateByParams(new CandidateFilter().generateCandidateFromParams(name, secondName, patr, directionsNames), directionService);
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

    public Candidate saveNewCandidate(NewCandidateDTO newCandidateDTO) {
        if (newCandidateDTO.isValid()) {
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName())
                    .secondName(newCandidateDTO.getSecond_name())
                    .patronymic(newCandidateDTO.getPatr())
                    .directions(newCandidateDTO.getDirections())
                    .candidateDescription(newCandidateDTO.getCandidateDesc())
                    .image(cvService.buildImage(newCandidateDTO.getImageFile()))
                    .curriculumVitae(cvService.buildCV(newCandidateDTO.getCvFile())).build();
            save(newCandidate);
            return newCandidate;
        } else throw new ObjectIsEmptyException();
    }


    public Candidate updateCandidate(Long candidateID, UpdateCandidateDTO updateCandidateDTO) {
        if (findCandidateByID(candidateID).isPresent()) {
            if (updateCandidateDTO.isValid()) {
                Candidate updatedCandidate = findCandidateByID(candidateID).get().update(updateCandidateDTO, cvService);
                update(updatedCandidate);
                return updatedCandidate;
            } else throw new ObjectIsEmptyException();
        } else throw new ObjectNotFoundException("Candidate", candidateID);

    }


}
