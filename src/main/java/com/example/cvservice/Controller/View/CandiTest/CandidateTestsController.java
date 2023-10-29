package com.example.cvservice.Controller.View.CandiTest;

import com.example.cvservice.DTO.Canditests.CandiTestDTO;
import com.example.cvservice.DTO.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.CandidatesTest;
import com.example.cvservice.Service.Candidate.CandidateService;
import com.example.cvservice.Service.CandidateTest.CandidateTestsService;
import com.example.cvservice.Service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/add")
    public String addNewCandidateTestPage(Model model) {
        model.addAttribute("allCandidates", candidateService.findAllCandidates());
        model.addAttribute("allTests", testService.findAll());
        model.addAttribute("newCandiTest", new CandiTestDTO());
        return "/canditests/add_new_candidate_test_page";
    }

    @GetMapping("/view/all")
    public String displayAllCandiTestsPage(Model model) {
        List<CandidatesTest> allCandidatesTests = candidateTestsService.findAll();

        Map<Candidate, List<CandidatesTest>> groupedTests = allCandidatesTests
                .stream()
                .collect(Collectors.groupingBy(CandidatesTest::getCandidate));

        model.addAttribute("groupedTestsByCandidate", groupedTests);
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
