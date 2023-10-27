package com.example.cvservice.Controller.Rest;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;
import com.example.cvservice.DTO.Candidate.UpdateCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Candidate.CandidateService;
import com.example.cvservice.Service.Candidate.InputCandidateVerification;
import com.example.cvservice.Service.Files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/task/candidates/rest")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;


    @PostMapping("/add/new")
    public ResponseEntity addNewCandidate(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        if (!InputCandidateVerification.doesCandidateIsEmpty(newCandidateDTO)) {
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName()).secondName(newCandidateDTO.getSecond_name()).patronymic(newCandidateDTO.getPatr()).directions((ArrayList<Direction>) newCandidateDTO.getDirections()).image(FileService.buildImage(newCandidateDTO.getImageFile())).curriculumVitae(FileService.buildCV(newCandidateDTO.getCvFile())).build();
            candidateService.save(newCandidate);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).build();
    }

    @DeleteMapping("/remove/candidate/{id}")
    public ResponseEntity removeCandidateByID(@PathVariable(value = "id") Long id) {
        candidateService.deleteByID(id);
        return ResponseEntity.status(200).build();
    }


    @PatchMapping("/update/candidate/{id}")
    public ResponseEntity updateCandidateByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandidateDTO updateCandidateDTO) throws IOException {

        if (!InputCandidateVerification.doesUpdatedCandidateIsEmpty(updateCandidateDTO)) {

            candidateService.updateByID(id, updateCandidateDTO);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).build();

    }


}
