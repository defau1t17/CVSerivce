package com.example.cvservice.service.Candidate;

import com.example.cvservice.controller.view.Candidate.CandidatesController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CandidateService.class);


    public List<Candidate> findAllCandidates() {
        return repository.findAll();
    }

    public Page<Candidate> findAllCandidatesByPageNumber(int pageNumber, int pageSize, String param, String direction, String name, String secondName, String patr, List<String> directionsNames) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction), param));
        Specification<Candidate> candidateSpecification = new CandidateFilter().filterCandidateByParams(new CandidateFilter().generateCandidateFromParams(name, secondName, patr, directionsNames), directionService);
        logger.info("new page with candidates created");
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
        logger.info("new candidate incoming");
        if (newCandidateDTO.isValid()) {
            logger.info("new candidate validated");
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName())
                    .secondName(newCandidateDTO.getSecond_name())
                    .patronymic(newCandidateDTO.getPatr())
                    .directions(newCandidateDTO.getDirections())
                    .candidateDescription(newCandidateDTO.getCandidateDesc())
                    .image(cvService.buildImage(newCandidateDTO.getImageFile()))
                    .cv(cvService.buildCV(newCandidateDTO.getCvFile())).build();
            save(newCandidate);
            logger.info("new candidate created");
            return newCandidate;
        } else
            logger.warn("new candidate has been rejected");
        throw new ObjectIsEmptyException();
    }

    public Candidate updateCandidate(Long candidateID, UpdateCandidateDTO updateCandidateDTO) {
        logger.info("updated candidate incoming");
        if (findCandidateByID(candidateID).isPresent()) {
            logger.info("updated candidate exists");
            if (updateCandidateDTO.isValid()) {
                logger.info("updated candidate valid");
                Candidate updatedCandidate = findCandidateByID(candidateID).get().update(updateCandidateDTO, cvService);
                update(updatedCandidate);
                logger.info("candidate updated ");
                return updatedCandidate;
            } else {
                logger.warn("updated candidate has been rejected");
                throw new ObjectIsEmptyException();
            }
        } else throw new ObjectNotFoundException("Candidate", candidateID);

    }


}
