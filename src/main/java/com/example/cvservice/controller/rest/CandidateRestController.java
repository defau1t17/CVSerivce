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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task/candidates/rest")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addNewCandidate(@ModelAttribute NewCandidateDTO newCandidateDTO) {
        if (!new InputCandidateVerification().doesCandidateIsEmpty(newCandidateDTO)) {
            Candidate newCandidate = Candidate.builder().name(newCandidateDTO.getName())
                    .secondName(newCandidateDTO.getSecond_name())
                    .patronymic(newCandidateDTO.getPatr())
                    .directions((ArrayList<Direction>) newCandidateDTO.getDirections())
                    .candidateDescription(newCandidateDTO.getCandidateDesc())
                    .image(FileService.buildImage(newCandidateDTO.getImageFile()))
                    .curriculumVitae(FileService.buildCV(newCandidateDTO.getCvFile())).build();
            candidateService.save(newCandidate);
            return ResponseEntity.ok("Новый пользователь успешно сохранен");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь не прошел валидацию");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Candidate> findCandidateByID(@PathVariable(value = "id") Long id) {
        if (candidateService.findCandidateByID(id).isPresent()) {
            return ResponseEntity.ok(candidateService.findCandidateByID(id).get());
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
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


    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateCandidateByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandidateDTO updateCandidateDTO) throws IOException {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        if (optionalCandidate.isPresent()) {
            candidateService.update(new UpdateCandidateData().updateCandidate(optionalCandidate.get(), updateCandidateDTO));
            return ResponseEntity.status(200).body("Пользователь успешно обновлен");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с таким ID не найден");

    }


}
