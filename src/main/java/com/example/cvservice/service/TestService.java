package com.example.cvservice.service;

import com.example.cvservice.dto.NewTestDTO;
import com.example.cvservice.dto.UpdateTestDTO;
import com.example.cvservice.entity.Test;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.service.Filter.TestFilter;
import com.example.cvservice.repository.TestRepository;
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
public class TestService implements EntityOperations {
    @Autowired
    private TestRepository testRepository;

    public Optional<Test> findTestByID(Long id) {
        return testRepository.findById(id);
    }

    public Optional<Test> findTestByName(String name) {
        return testRepository.findByName(name);
    }

    public Page<Test> findTestsByParams(int page, int size, String name, String description, List<String> directionNames, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        return testRepository.findAll(TestFilter.filterTests(TestFilter.generateFilterFromParams(name, description, directionNames)), pageable);
    }
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Object object) {
        testRepository.save((Test) object);
    }

    @Transactional
    @Override
    public void update(Object object) {
        testRepository.save((Test) object);
    }

    public Test saveNewTest(NewTestDTO newTestDTO) {
        if (!findTestByName(newTestDTO.getName()).isPresent()) {
            if (newTestDTO.isValid()) {
                Test newTest = Test.builder().name(newTestDTO.getName()).description(newTestDTO.getDescription()).specializations(newTestDTO.getTestSpecializations()).build();
                save(newTest);
                return newTest;
            } else {
                throw new ObjectIsEmptyException();
            }
        }
        throw new ObjectAlreadyExistsException("Test", newTestDTO.getName());
    }

    public Test updateTest(Long testID, UpdateTestDTO updateTestDTO) {
        if (findTestByID(testID).isPresent()) {
            if (updateTestDTO.isValid()) {
                Test updatedTest = findTestByID(testID).get().update(updateTestDTO);
                update(updatedTest);
                return updatedTest;
            } else {
                throw new ObjectIsEmptyException();
            }
        } else throw new ObjectNotFoundException("Test", testID);
    }
}
