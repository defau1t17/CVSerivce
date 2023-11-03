package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Candidate.InputCandidateVerification;
import com.example.cvservice.service.Candidate.UpdateCandidateData;
import com.example.cvservice.service.Files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/task/candidates/rest")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/add/new")
    public ResponseEntity addNewCandidate(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        if (!InputCandidateVerification.doesCandidateIsEmpty(newCandidateDTO)) {
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName())
                    .secondName(newCandidateDTO.getSecond_name())
                    .patronymic(newCandidateDTO.getPatr())
                    .directions((ArrayList<Direction>) newCandidateDTO.getDirections())
                    .candidateDescription(newCandidateDTO.getCandidateDesc())
                    .image(FileService.buildImage(newCandidateDTO.getImageFile()))
                    .curriculumVitae(FileService.buildCV(newCandidateDTO.getCvFile())).build();
            candidateService.save(newCandidate);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).build();
    }

    @DeleteMapping("/remove/candidate/{id}")
    public ResponseEntity removeCandidateByID(@PathVariable(value = "id") Long id) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        if (optionalCandidate.isPresent()) {
            candidateService.delete(optionalCandidate.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/update/candidate/{id}")
    public ResponseEntity updateCandidateByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandidateDTO updateCandidateDTO) throws IOException {
        if (!InputCandidateVerification.doesUpdatedCandidateIsEmpty(updateCandidateDTO)) {
            Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
            if (optionalCandidate.isPresent()) {
                candidateService.update(new UpdateCandidateData().updateCandidate(optionalCandidate.get(), updateCandidateDTO));
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(409).build();

    }


}
