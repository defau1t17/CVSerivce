package com.example.cvservice.service.Test;

import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Filter.TestFilter;
import com.example.cvservice.repository.Test.TestRepository;
import com.example.cvservice.service.EntityOperations;
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

    public Page<Test> findTestsByParams(int page, int size, boolean filter, String name, String description, List<String> directionNames, String sort, String direction) {
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

    @Override
    public void delete(Object object) {
        testRepository.delete((Test) object);
    }
}
