package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.NewCandidateDTO;
import com.example.cvservice.dto.UpdateCandidateDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Candidate;
import com.example.cvservice.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/candidates")
@Tag(name = "Candidates", description = "API FOR MANAGING CANDIDATES")
public class CandidateRestController {

    Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Add new Candidate")
    @ApiResponse(responseCode = "200", description = "New candidate has been added")
    @ApiResponse(responseCode = "409", description = "Error adding new Candidate")
    @PostMapping(value = "/")
    public ResponseEntity<?> addNewCandidate(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        Candidate newCandidate = candidateService.saveNewCandidate(newCandidateDTO);
        logger.info("new candidate [id : " + newCandidate.getId() + " ] saved");
        return ResponseEntity.ok(newCandidate);
    }

    @Operation(summary = "Get Candidate by ID")
    @ApiResponse(responseCode = "200", description = "Candidate's Data")
    @ApiResponse(responseCode = "404", description = "Candidate with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<?> findCandidateByID(@PathVariable(value = "id") Long id) {
        if (candidateService.findCandidateByID(id).isPresent()) {
            return ResponseEntity.ok(candidateService.findCandidateByID(id).get());
        } else return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get Candidates by params")
    @ApiResponse(responseCode = "200", description = "Candidates Data")
    @GetMapping("")
    public ResponseEntity<List<Candidate>> findFilteredCandidates(@RequestParam(required = false) Optional<Integer> page,
                                                                  @RequestParam(required = false) Optional<Integer> size,
                                                                  @RequestParam(required = false, defaultValue = "name") String sort,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String secondName,
                                                                  @RequestParam(required = false) String patronymic,
                                                                  @RequestParam(required = false) List<String> dir) {
        return ResponseEntity.ok(candidateService.findAllCandidatesByPageNumber(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), sort, direction, name, secondName, patronymic, dir).getContent());
    }

    @Operation(summary = "Update Candidate by ID")
    @ApiResponse(responseCode = "200", description = "Candidate has been updated")
    @ApiResponse(responseCode = "404", description = "Candidate with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCandidateByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandidateDTO updateCandidateDTO) throws IOException {
        Candidate updatedCandidate = candidateService.updateCandidate(id, updateCandidateDTO);
        logger.info("candidate [id : " + updatedCandidate.getId() + " ] updated");
        return ResponseEntity.ok(updatedCandidate);
    }


}
