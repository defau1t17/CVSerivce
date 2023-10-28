package com.example.cvservice.Service.Test;

import com.example.cvservice.Entity.Main.Test;
import com.example.cvservice.Repository.Test.TestRepository;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
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
