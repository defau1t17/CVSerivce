package com.example.cvservice.controller.view;


import com.example.cvservice.entity.CurriculumVitae;
import com.example.cvservice.entity.Candidate;
import com.example.cvservice.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private CandidateService candidateService;
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable(value = "id") Long id) {
        System.out.println("i'm here");
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        CurriculumVitae curriculumVitae = null;
        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
            curriculumVitae = candidate.getCv();
        }
        if (curriculumVitae != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + curriculumVitae.getCvFileName());

            return new ResponseEntity<>(curriculumVitae.getCvData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
