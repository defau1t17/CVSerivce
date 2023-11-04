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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task/canditests/rest")
public class CandidateTestsRestController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;


    @Autowired
    private CandidateTestsService candidateTestsService;

    @PostMapping("/add")
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
                return ResponseEntity.ok("Новый тест кандидата успешно добавлен");
            } else return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка добавленияи нового теста кандидата");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка добавления. Тест или Кандидат с таким ID не найдены");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateCandiTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateCandiTestDTO candiTestDTO) {
        if (candidateTestsService.findCandidateTestByID(id).isPresent()) {
            if (candidateService.findCandidateByID(candiTestDTO.getCandidateID()).isPresent() && testService.findTestByID(candiTestDTO.getTestID()).isPresent()) {
                if (!new InputCandidateTestValidation().isUpdateCandidateTestDataEmpty(candiTestDTO) && new InputCandidateTestValidation().isCandidateTestExists(candidateTestsService, candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get())) {
                    candidateTestsService.update(new UpdateCandidateTestData().updateCandidateTest(candidateTestsService.findCandidateTestByID(candiTestDTO.getCandiTestID()).get(), candiTestDTO));
                    return ResponseEntity.status(HttpStatus.OK).body("Тест кандидата успешно обновлен");
                }
            } else ResponseEntity.status(HttpStatus.NOT_FOUND).body("Кандидат или Тест с таким ID не найдены");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тест кандидата с таким ID не найден");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка при обновлении тест кандидата");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CandidatesTest> getCandidateTestByID(@PathVariable(value = "id") Long id) {
        Optional<CandidatesTest> optionalCandidatesTest = candidateTestsService.findCandidateTestByID(id);
        return optionalCandidatesTest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/get")
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
