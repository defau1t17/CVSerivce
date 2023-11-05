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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/canditests")
@Tag(name = "Candidates Tests", description = "API FOR MANAGING CANDIDATES TESTS")
public class CandidateTestsRestController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;


    @Autowired
    private CandidateTestsService candidateTestsService;

    @Operation(summary = "Add new Candidate's Test")
    @ApiResponse(responseCode = "200", description = "New Candidate's Test has been added")
    @ApiResponse(responseCode = "409", description = "Error while new Candidate's Test. Candidate's Test exists or DTO empty")
    @ApiResponse(responseCode = "403", description = "Error while new Candidate's Test. Test or Candidate not found")
    @PostMapping("/")
    public ResponseEntity<String> addNewCandidateTest(@ModelAttribute CandiTestDTO candiTestDTO) {
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
            if (!new InputCandidateTestValidation().isCandidateTestExists(candidateTestsService, newCandidateTest) && !new InputCandidateTestValidation().isCandidateTestEmpty(newCandidateTest)) {
                candidateTestsService.save(newCandidateTest);
                return ResponseEntity.ok("New Candidate's Test has been added");
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error while new Candidate's Test. Candidate's Test exists or DTO empty");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error while new Candidate's Test. Test or Candidate not found");
    }

    @Operation(summary = "Update Candidate's Test by ID")
    @ApiResponse(responseCode = "200", description = "Candidate's Test has been updated")
    @ApiResponse(responseCode = "404", description = "Error while updating Candidate's Test. Test not found")
    @ApiResponse(responseCode = "404", description = "Error while updating Candidate's Test. Candidate not found")
    @ApiResponse(responseCode = "409", description = "Error while updating Candidate's Test.")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCandiTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandiTestDTO candiTestDTO) {
        if (candidateTestsService.findCandidateTestByID(id).isPresent()) {
            if (candidateService.findCandidateByID(candiTestDTO.getCandidateID()).isPresent() && testService.findTestByID(candiTestDTO.getTestID()).isPresent()) {
                if (!new InputCandidateTestValidation().isUpdateCandidateTestDataEmpty(candiTestDTO) && new InputCandidateTestValidation().isCandidateTestExists(candidateTestsService, candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get())) {
                    candidateTestsService.update(new UpdateCandidateTestData().updateCandidateTest(candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get(), candiTestDTO));
                    return ResponseEntity.status(HttpStatus.OK).body("Candidate's Test has been updated");
                }
            } else
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while updating Candidate's Test. Candidate not found");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while updating Candidate's Test. Test not found");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error while updating Candidate's Test.");
    }


    @Operation(summary = "Get Candidate's Test  ID")
    @ApiResponse(responseCode = "200", description = "Candidate's Test")
    @ApiResponse(responseCode = "404", description = "Candidate's Test with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<CandidatesTest> getCandidateTestByID(@PathVariable(value = "id") Long id) {
        Optional<CandidatesTest> optionalCandidatesTest = candidateTestsService.findCandidateTestByID(id);
        return optionalCandidatesTest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get Candidates Tests by params")
    @ApiResponse(responseCode = "200", description = "Candidates Tests")
    @GetMapping("")
    public ResponseEntity<List<CandidatesTest>> getCandidateTestsByParams(@RequestParam(required = false) Optional<Integer> page,
                                                                          @RequestParam(required = false) Optional<Integer> size,
                                                                          @RequestParam(required = false, defaultValue = "candidate.name") String sort,
                                                                          @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                          @RequestParam(required = false, value = "cName") String candidateName,
                                                                          @RequestParam(required = false, value = "cSecondName") String candidateSecondName,
                                                                          @RequestParam(required = false, value = "cPatr") String candidatePatronymic,
                                                                          @RequestParam(required = false, value = "tName") String testName,
                                                                          @RequestParam(required = false, value = "tDesc") String testDesc,
                                                                          @RequestParam(required = false) List<String> dirNames,
                                                                          @RequestParam(required = false) Optional<Integer> fromMark,
                                                                          @RequestParam(required = false) Optional<Integer> toMark,
                                                                          @RequestParam(required = false) LocalDate fromDate,
                                                                          @RequestParam(required = false) LocalDate toDate) {
        return ResponseEntity.ok(candidateTestsService.findCandidatesTestsByParams(page.orElse(0), size.orElse(10), candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, dirNames, fromDate, toDate, fromMark.orElse(0), toMark.orElse(100), sort, direction).getContent());
    }


}
