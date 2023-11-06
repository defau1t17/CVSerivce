package com.example.cvservice.service;

import com.example.cvservice.dto.NewSpecializationDTO;
import com.example.cvservice.dto.UpdateSpecializationDTO;
import com.example.cvservice.entity.Specialization;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.service.Filter.SpecializationFilter;
import com.example.cvservice.repository.SpecializationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class SpecializationService implements EntityOperations {

    @Autowired
    private SpecializationsRepository repository;


    public List<Specialization> findAll() {
        return repository.findAll();
    }

    public List<Specialization> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    public Page<Specialization> findSpecializationsByParams(int page, int size, String name, String description, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        return repository.findAll(new SpecializationFilter().filterSpecialization(new SpecializationFilter().generateSpecializationFilterFromParams(name, description)), pageable);
    }


    public Optional<Specialization> findSpecializationByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Specialization> findSpecializationByID(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public void save(Object object) {
        repository.save((Specialization) object);
    }

    @Transactional
    @Override
    public void update(Object object) {
        repository.save((Specialization) object);
    }

    public Specialization saveNewSpecialization(NewSpecializationDTO newSpecializationDTO) {
        if (!findSpecializationByName(newSpecializationDTO.getName()).isPresent()) {
            if (newSpecializationDTO.isValid()) {
                Specialization specialization = Specialization.builder().name(newSpecializationDTO.getName()).description(newSpecializationDTO.getDescription()).build();
                save(specialization);
                return specialization;
            } else {
                throw new ObjectIsEmptyException();
            }
        }
        throw new ObjectAlreadyExistsException("Specialization", newSpecializationDTO.getName());
    }

    public Specialization updateSpecialization(Long directionID, UpdateSpecializationDTO updateSpecializationDTO) {
        if (findSpecializationByID(directionID).isPresent()) {
            if (updateSpecializationDTO.isValid()) {
                Specialization updatedSpecialization = findSpecializationByID(directionID).get().update(updateSpecializationDTO);
                update(updatedSpecialization);
                return updatedSpecialization;
            } else {
                throw new ObjectIsEmptyException();
            }
        } else throw new ObjectNotFoundException("Specialization", directionID);
    }


}
