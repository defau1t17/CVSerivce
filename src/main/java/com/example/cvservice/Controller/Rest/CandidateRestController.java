package com.example.cvservice.Controller.Rest;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Candidate.CandidateService;
import com.example.cvservice.Service.Candidate.InputCandidateVerification;
import com.example.cvservice.Service.Files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/task/candidates/rest")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;


    @PostMapping("/add/new")
    public ResponseEntity test(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        System.out.println("i'm here");
        System.out.println(newCandidateDTO.toString());
        System.out.println(newCandidateDTO.getDirections().size());

        if (!InputCandidateVerification.doesCandidateIsEmpty(newCandidateDTO)) {
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName()).secondName(newCandidateDTO.getSecond_name()).patronymic(newCandidateDTO.getPatr()).directions((ArrayList<Direction>) newCandidateDTO.getDirections()).image(FileService.buildImage(newCandidateDTO.getImageFile())).curriculumVitae(FileService.buildCV(newCandidateDTO.getCvFile())).build();
            candidateService.save(newCandidate);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).build();
    }


}
