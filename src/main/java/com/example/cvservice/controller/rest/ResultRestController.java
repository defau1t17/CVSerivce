package com.example.cvservice.controller.rest;


import com.example.cvservice.dto.ResultDTO;
import com.example.cvservice.dto.UpdateResultDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Result;
import com.example.cvservice.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/results")
@Tag(name = "Results", description = "API FOR MANAGING RESULTS")
public class ResultRestController {
    @Autowired
    private ResultService resultService;

    Logger logger = LoggerFactory.getLogger(ResultRestController.class);


    @Operation(summary = "Add new Candidate's Test")
    @ApiResponse(responseCode = "200", description = "New Candidate's Test has been added")
    @ApiResponse(responseCode = "409", description = "Error while new Candidate's Test. Candidate's Test exists or DTO empty")
    @ApiResponse(responseCode = "403", description = "Error while new Candidate's Test. Test or Candidate not found")
    @PostMapping("/")
    public ResponseEntity<?> addNewCandidateTest(@ModelAttribute ResultDTO resultDTO) {
        Result result = resultService.saveNewResult(resultDTO);
        logger.info("result  [id: " + result.getId() + " ] created");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Update Candidate's Test by ID")
    @ApiResponse(responseCode = "200", description = "Candidate's Test has been updated")
    @ApiResponse(responseCode = "404", description = "Error while updating Candidate's Test. Test not found")
    @ApiResponse(responseCode = "404", description = "Error while updating Candidate's Test. Candidate not found")
    @ApiResponse(responseCode = "409", description = "Error while updating Candidate's Test.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCandiTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateResultDTO updateResultDTO) {
        Result result = resultService.updateResult(id, updateResultDTO);
        logger.info("result  [id: " + result.getId() + " ] updated");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get Candidate's Test  ID")
    @ApiResponse(responseCode = "200", description = "Candidate's Test")
    @ApiResponse(responseCode = "404", description = "Candidate's Test with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<Result> getCandidateTestByID(@PathVariable(value = "id") Long id) {
        Optional<Result> optionalCandidatesTest = resultService.findResultByID(id);
        return optionalCandidatesTest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get Candidates Tests by params")
    @ApiResponse(responseCode = "200", description = "Candidates Tests")
    @GetMapping("")
    public ResponseEntity<List<Result>> getCandidateTestsByParams(@RequestParam(required = false) Optional<Integer> page,
                                                                  @RequestParam(required = false) Optional<Integer> size,
                                                                  @RequestParam(required = false, defaultValue = "candidate.name") String sort,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                  @RequestParam(required = false, value = "cName") String candidateName,
                                                                  @RequestParam(required = false, value = "cSecondName") String candidateSecondName,
                                                                  @RequestParam(required = false, value = "cPatr") String candidatePatronymic,
                                                                  @RequestParam(required = false, value = "tName") String testName,
                                                                  @RequestParam(required = false, value = "tDesc") String testDesc,
                                                                  @RequestParam(required = false) List<String> specNames,
                                                                  @RequestParam(required = false) Optional<Integer> fromMark,
                                                                  @RequestParam(required = false) Optional<Integer> toMark,
                                                                  @RequestParam(required = false) LocalDate fromDate,
                                                                  @RequestParam(required = false) LocalDate toDate) {
        return ResponseEntity.ok(resultService.findResultsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_NUMBER), candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, specNames, fromDate, toDate, fromMark.orElse(0), toMark.orElse(100), sort, direction).getContent());
    }


}
