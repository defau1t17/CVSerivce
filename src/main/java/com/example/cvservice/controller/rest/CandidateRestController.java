package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Candidate.InputCandidateVerification;
import com.example.cvservice.service.Candidate.UpdateCandidateData;
import com.example.cvservice.service.Files.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task/candidates/rest")
@Tag(name = "Кандидаты", description = "API для управления кандидатами")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Добавить нового кандидата")
    @ApiResponse(responseCode = "200", description = "Кандидат успешно добавлен")
    @ApiResponse(responseCode = "409", description = "Ошибка при добавлении каниддата. Данные кандидата пусты")
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

    @Operation(summary = "Получить кандидата по ID")
    @ApiResponse(responseCode = "200", description = "Данные кандидата")
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
    @GetMapping("/get/{id}")
    public ResponseEntity<Candidate> findCandidateByID(@PathVariable(value = "id") Long id) {
        if (candidateService.findCandidateByID(id).isPresent()) {
            return ResponseEntity.ok(candidateService.findCandidateByID(id).get());
        } else return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить кандидата по параметрам")
    @ApiResponse(responseCode = "200", description = "Данные кандидата")
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

    @Operation(summary = "Обновить кандидата по ID")
    @ApiResponse(responseCode = "200", description = "Данные кандидата  успешно обновлены")
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
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
