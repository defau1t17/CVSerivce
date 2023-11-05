package com.example.cvservice.service.Test;

import com.example.cvservice.dto.Test.NewTestDTO;
import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.service.Filter.TestFilter;
import com.example.cvservice.repository.Test.TestRepository;
import com.example.cvservice.service.EntityOperations;
import com.example.cvservice.service.Result.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(TestService.class);

    public Optional<Test> findTestByID(Long id) {
        return testRepository.findById(id);
    }

    public Optional<Test> findTestByName(String name) {
        return testRepository.findByName(name);
    }

    public Page<Test> findTestsByParams(int page, int size, String name, String description, List<String> directionNames, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        logger.info("page with tests has been created");
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
        logger.info("new test incoming");
        if (!findTestByName(newTestDTO.getName()).isPresent()) {
            logger.info("new test doesen't exists");
            if (newTestDTO.isValid()) {
                logger.info("new test valid");
                Test newTest = Test.builder().name(newTestDTO.getName()).description(newTestDTO.getDescription()).directions(newTestDTO.getTestDirections()).build();
                save(newTest);
                logger.info("new test created");
                return newTest;
            } else {
                logger.warn("new test has been rejected");
                throw new ObjectIsEmptyException();
            }
        }
        throw new ObjectAlreadyExistsException("Test", newTestDTO.getName());
    }

    public Test updateTest(Long testID, UpdateTestDTO updateTestDTO) {
        logger.info("updated test incoming");

        if (findTestByID(testID).isPresent()) {
            logger.info("updated test exists");
            if (updateTestDTO.isValid()) {
                logger.info("updated test valid");
                Test updatedTest = findTestByID(testID).get().update(updateTestDTO);
                update(updatedTest);
                logger.info("test updated");
                return updatedTest;
            } else {
                logger.warn("updated test has been rejected");
                throw new ObjectIsEmptyException();
            }
        } else throw new ObjectNotFoundException("Test", testID);

    }


}
