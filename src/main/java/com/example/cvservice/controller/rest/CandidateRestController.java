package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.service.Candidate.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Add new Candidate")
    @ApiResponse(responseCode = "200", description = "New candidate has been added")
    @ApiResponse(responseCode = "409", description = "Error adding new Candidate")
    @PostMapping(value = "/")
    public ResponseEntity<?> addNewCandidate(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        Candidate newCandidate = candidateService.saveNewCandidate(newCandidateDTO);
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
        return ResponseEntity.ok(candidateService.findAllCandidatesByPageNumber(page.orElse(0), size.orElse(10), sort, direction, name, secondName, patronymic, dir).getContent());
    }

    @Operation(summary = "Update Candidate by ID")
    @ApiResponse(responseCode = "200", description = "Candidate has been updated")
    @ApiResponse(responseCode = "404", description = "Candidate with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCandidateByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandidateDTO updateCandidateDTO) throws IOException {
        Candidate updatedCandidate = candidateService.updateCandidate(id, updateCandidateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }


}
