package com.example.cvservice.Controller.View;

import com.example.cvservice.Service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/view/all")
    public String allCandidatesPage(Model model) {

        return "/candidates/all_candidates_page";
    }

    @GetMapping("/add")
    public String displayAddNewCandidatePage() {
        return "/candidates/add_new_candidate_page";
    }


}
