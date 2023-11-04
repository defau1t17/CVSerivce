package com.example.cvservice.controller.view.CandiTest;

import com.example.cvservice.dto.Canditests.CandiTestDTO;
import com.example.cvservice.dto.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.CandidatesTest;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.CandidateTest.CandidateTestsService;
import com.example.cvservice.service.Direction.DirectionService;
import com.example.cvservice.service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task/canditests/")
public class CandidateTestsController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;

    @Autowired
    private CandidateTestsService candidateTestsService;

    @Autowired
    private DirectionService directionService;

    @GetMapping("/add")
    public String addNewCandidateTestPage(Model model) {
        model.addAttribute("allCandidates", candidateService.findAllCandidates());
        model.addAttribute("allTests", testService.findAll());
        model.addAttribute("newCandiTest", new CandiTestDTO());
        return "/canditests/add_new_candidate_test_page";
    }

    @GetMapping("/all")
    public String displayAllCandiTestsPage(@RequestParam(required = false) Optional<Integer> page,
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
                                           @RequestParam(required = false) LocalDate toDate,
                                           Model model) {
        Page<CandidatesTest> allCandidatesTests = candidateTestsService.findCandidatesTestsByParams(page.orElse(0), size.orElse(10), candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, dirNames, fromDate, toDate, fromMark.orElse(0), toMark.orElse(100), sort, direction);
        model.addAttribute("cName", candidateName);
        model.addAttribute("cSecondName", candidateSecondName);
        model.addAttribute("cPatr", candidatePatronymic);
        model.addAttribute("tName", testName);
        model.addAttribute("tDesc", testDesc);
        model.addAttribute("dirNames", dirNames);
        model.addAttribute("fromMark", fromMark.orElse(0));
        model.addAttribute("toMark", toMark.orElse(100));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("pageSize", size.orElse(10));

        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("allCandidateTests", allCandidatesTests);
        model.addAttribute("allTests", testService.findAll());


        return "/canditests/all_candidates_tests_page";
    }


    @GetMapping("/canditest/candidate/{id}")
    public String displayCandidateTestsPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        List<CandidatesTest> allCandidateTests = null;

        if (optionalCandidate.isPresent()) {
            allCandidateTests = candidateTestsService.findAllTestsByCandidate(optionalCandidate.get());
            model.addAttribute("candidate", optionalCandidate.get());
        }
        model.addAttribute("allCandidateTests", allCandidateTests);
        return "/canditests/candidate_tests_page";
    }

    @GetMapping("/canditest/edit/candidate/{id}")
    public String editCandidateTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<CandidatesTest> optionalCandidatesTest = candidateTestsService.findCandidateTestByID(id);
        UpdateCandiTestDTO candiTestDTO = null;
        CandidatesTest candidateTest = null;

        if (optionalCandidatesTest.isPresent()) {
            candidateTest = optionalCandidatesTest.get();
            candiTestDTO = UpdateCandiTestDTO.builder()
                    .candiTestID(candidateTest.getId())
                    .candidateID(candidateTest.getCandidate().getId())
                    .testID(candidateTest.getTest().getId())
                    .testName(candidateTest.getTest().getName())
                    .date(candidateTest.getGrade().getDate())
                    .mark(candidateTest.getGrade().getMark())
                    .build();
            model.addAttribute("candidate", candidateTest.getCandidate());
        }
        model.addAttribute("candidateTest", candiTestDTO);
        model.addAttribute("allTests", testService.findAll());
        return "/canditests/edit_candidate_test_page";
    }

}
