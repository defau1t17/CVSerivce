package com.example.cvservice.controller.rest;


import com.example.cvservice.dto.Canditests.CandiTestDTO;
import com.example.cvservice.dto.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.entity.grade.TestGrade;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.CandidatesTest;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.CandidateTest.CandidateTestsService;
import com.example.cvservice.service.CandidateTest.InputCandidateTestValidation;
import com.example.cvservice.service.CandidateTest.UpdateCandidateTestData;
import com.example.cvservice.service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/task/canditests/rest")
public class CandidateTestsRestController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;


    @Autowired
    private CandidateTestsService candidateTestsService;

    @PostMapping("/add/new")
    public ResponseEntity addNewCandidateTest(@ModelAttribute CandiTestDTO candiTestDTO) {
        Candidate candidate = null;
        Test test = null;
        TestGrade testGrade = null;
        if (candidateService.findCandidateByID(candiTestDTO.getCandidateID()).isPresent() && testService.findTestByID(candiTestDTO.getTestID()).isPresent()) {
            candidate = candidateService.findCandidateByID(candiTestDTO.getCandidateID()).get();
            test = testService.findTestByID(candiTestDTO.getTestID()).get();
            testGrade = TestGrade.builder().date(candiTestDTO.getDate()).mark(candiTestDTO.getMark()).build();

            CandidatesTest newCandidateTest = CandidatesTest.builder().
                    candidate(candidate)
                    .test(test)
                    .grade(testGrade)
                    .build();
            if (!InputCandidateTestValidation.isCandidateTestExists(candidateTestsService, newCandidateTest) && !InputCandidateTestValidation.isCandidateTestEmpty(newCandidateTest)) {
                candidateTestsService.save(newCandidateTest);
                return ResponseEntity.ok().build();
            } else return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/canditest/update/{id}")
    public ResponseEntity updateCandiTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandiTestDTO candiTestDTO) {
        System.out.println(candiTestDTO.toString());
        if (candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).isPresent()) {
            if (candidateService.findCandidateByID(candiTestDTO.getCandidateID()).isPresent() && testService.findTestByID(candiTestDTO.getTestID()).isPresent()) {
                if (!InputCandidateTestValidation.isUpdateCandidateTestDataEmpty(candiTestDTO) && !InputCandidateTestValidation.isCandidateTestExists(candidateTestsService, candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get())) {
                    candidateTestsService.update(UpdateCandidateTestData.updateCandidateTest(candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get(), candiTestDTO));
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
            } else ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/canditest/delete/{id}")
    public ResponseEntity deleteCandidateTestByID(@PathVariable(value = "id") Long id) {
        Optional<CandidatesTest> optionalCandidatesTest = candidateTestsService.findCandidateTestByID(id);
        if (optionalCandidatesTest.isPresent()) {
            candidateTestsService.delete(optionalCandidatesTest.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
