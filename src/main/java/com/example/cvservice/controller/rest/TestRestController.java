package com.example.cvservice.controller.rest;


import com.example.cvservice.dto.Test.NewTestDTO;
import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Test.InputTestValidation;
import com.example.cvservice.service.Test.TestService;
import com.example.cvservice.service.Test.UpdateDataTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/task/tests/rest")
public class TestRestController {

    @Autowired
    private TestService testService;

    @PostMapping("/add/new")
    public ResponseEntity addNewTest(@ModelAttribute NewTestDTO newTestDTO) {
        if (!InputTestValidation.isNewTestExists(testService, newTestDTO) && !InputTestValidation.isNewTestEmpty(newTestDTO)) {
            Test newTest = Test.builder().name(newTestDTO.getName()).description(newTestDTO.getDescription()).directions(newTestDTO.getTestDirections()).build();
            testService.save(newTest);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/test/delete/{id}")
    public ResponseEntity deleteTestByID(@PathVariable(value = "id") Long id) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        if (optionalTest.isPresent()) {
            testService.delete(optionalTest.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/test/update/{id}")
    public ResponseEntity updateTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateTestDTO updateTestDTO) {
        Optional<Test> optionalTestTest = testService.findTestByID(id);
        if (optionalTestTest.isPresent()) {
            if (!InputTestValidation.isUpdateTestEmpty(updateTestDTO)) {
                testService.update(new UpdateDataTest().updateTest(optionalTestTest.get(), updateTestDTO));
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


}
