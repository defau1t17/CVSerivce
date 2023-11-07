package com.example.cvservice.controller.view;

import com.example.cvservice.dto.ResultDTO;
import com.example.cvservice.dto.UpdateResultDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Candidate;
import com.example.cvservice.entity.Result;
import com.example.cvservice.service.CandidateService;
import com.example.cvservice.service.ResultService;
import com.example.cvservice.service.SpecializationService;
import com.example.cvservice.service.TestService;
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
import java.util.Optional;

@Controller
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private SpecializationService specializationService;


    @GetMapping("/add")
    public String addNewResultPage(Model model) {
        model.addAttribute("allCandidates", candidateService.findAllCandidates());
        model.addAttribute("allTests", testService.findAll());
        model.addAttribute("newResult", new ResultDTO());
        return "results/add_new_result_page";
    }

    @GetMapping("")
    public String displayAllResultsPage(@RequestParam(required = false) Optional<Integer> page,
                                        @RequestParam(required = false) Optional<Integer> size,
                                        @RequestParam(required = false, defaultValue = "candidate.name") String sort,
                                        @RequestParam(required = false, defaultValue = "ASC") String direction,
                                        @RequestParam(required = false, value = "cName") String candidateName,
                                        @RequestParam(required = false, value = "cSecondName") String candidateSecondName,
                                        @RequestParam(required = false, value = "cPatr") String candidatePatronymic,
                                        @RequestParam(required = false, value = "tName") String testName,
                                        @RequestParam(required = false, value = "tDesc") String testDesc,
                                        @RequestParam(required = false) List<String> specName,
                                        @RequestParam(required = false) Optional<Integer> fromMark,
                                        @RequestParam(required = false) Optional<Integer> toMark,
                                        @RequestParam(required = false) LocalDate fromDate,
                                        @RequestParam(required = false) LocalDate toDate,
                                        Model model) {
        Page<Result> allResults = resultService.findResultsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), candidateName, candidateSecondName, candidatePatronymic, testName, testDesc, specName, fromDate, toDate, fromMark.orElse(0), toMark.orElse(100), sort, direction);
        model.addAttribute("cName", candidateName);
        model.addAttribute("cSecondName", candidateSecondName);
        model.addAttribute("cPatr", candidatePatronymic);
        model.addAttribute("tName", testName);
        model.addAttribute("tDesc", testDesc);
        model.addAttribute("specNames", specName);
        model.addAttribute("fromMark", fromMark.orElse(0));
        model.addAttribute("toMark", toMark.orElse(100));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("allSpecializations", specializationService.findAll());
        model.addAttribute("allResults", allResults);
        model.addAttribute("allTests", testService.findAll());
        return "results/all_results_page";
    }

    @GetMapping("/{id}")
    public String displayResultPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        List<Result> allResults = null;
        if (optionalCandidate.isPresent()) {
            allResults = resultService.findAllResultsByCandidate(optionalCandidate.get());
            model.addAttribute("candidate", optionalCandidate.get());
        }
        model.addAttribute("allResults", allResults);
        return "results/result_page";
    }

    @GetMapping("/edit/{id}")
    public String editCandidateTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Result> optionalCandidatesTest = resultService.findResultByID(id);
        UpdateResultDTO resultDTO = null;
        Result candidateTest = null;

        if (optionalCandidatesTest.isPresent()) {
            candidateTest = optionalCandidatesTest.get();
            resultDTO = UpdateResultDTO.builder()
                    .resultID(candidateTest.getId())
                    .candidateID(candidateTest.getCandidate().getId())
                    .testID(candidateTest.getTest().getId())
                    .testName(candidateTest.getTest().getName())
                    .date(candidateTest.getGrade().getDate())
                    .mark(candidateTest.getGrade().getMark())
                    .build();
            model.addAttribute("candidate", candidateTest.getCandidate());
        }
        model.addAttribute("result", resultDTO);
        model.addAttribute("allTests", testService.findAll());

        return "results/edit_result_page";
    }

}
