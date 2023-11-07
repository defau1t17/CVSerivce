package com.example.cvservice.controller.view;

import com.example.cvservice.dto.NewCandidateDTO;
import com.example.cvservice.dto.UpdateCandidateDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Candidate;
import com.example.cvservice.service.CandidateService;
import com.example.cvservice.service.SpecializationService;
import com.example.cvservice.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidates")
public class CandidatesController {

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CandidateService candidateService;



    @GetMapping("")
    public String allCandidatesPage(Model model,
                                    @RequestParam(required = false) Optional<Integer> page,
                                    @RequestParam(required = false) Optional<Integer> size,
                                    @RequestParam(required = false, defaultValue = "name") String sort,
                                    @RequestParam(required = false, defaultValue = "ASC") String direction,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String secondName,
                                    @RequestParam(required = false) String patronymic,
                                    @RequestParam(required = false) List<String> spec) {
        Page<Candidate> allCandidatesByPageNumber = candidateService.findAllCandidatesByPageNumber(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), sort, direction, name, secondName, patronymic, spec);
        model.addAttribute("allCandidates", allCandidatesByPageNumber);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("pages", allCandidatesByPageNumber.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("allSpecializations", specializationService.findAll());
        model.addAttribute("filterName", name);
        model.addAttribute("filterSecondName", secondName);
        model.addAttribute("filterPatr", patronymic);
        model.addAttribute("filterSpec", spec);

        return "candidates/all_candidates_page";
    }


    @GetMapping("/add")
    public String displayAddNewCandidatePage(Model model) {
        model.addAttribute("allSpecializations", specializationService.findAll());
        model.addAttribute("newCandidate", new NewCandidateDTO());

        return "candidates/add_new_candidate_page";
    }

    @GetMapping("/{id}")
    public String displayCandidatePage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
        }
        model.addAttribute("candidate", candidate);

        return "candidates/candidate_page";
    }

    @GetMapping("/edit/{id}")
    public String displayUpdateCandidatePage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        UpdateCandidateDTO updateCandidateDTO = null;

        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
            if (candidate.getCv() != null) {
                updateCandidateDTO = UpdateCandidateDTO.builder().id(candidate.getId())
                        .name(candidate.getName())
                        .second_name(candidate.getSecondName())
                        .patr(candidate.getPatronymic())
                        .specializations(candidate.getSpecializations())
                        .candidateDesc(candidate.getCandidateDescription())
                        .cvFile(CVService.createMultipartFileFormByteArray(candidate.getCv().getCvData(), "application/octet-stream", candidate.getCv().getCvFileName()))
                        .imageFile(CVService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            } else {
                updateCandidateDTO = UpdateCandidateDTO.builder().id(candidate.getId())
                        .name(candidate.getName())
                        .second_name(candidate.getSecondName())
                        .patr(candidate.getPatronymic())
                        .candidateDesc(candidate.getCandidateDescription())

                        .specializations(candidate.getSpecializations())
                        .cvFile(null)
                        .imageFile(CVService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            }
        }
        model.addAttribute("allSpecializations", specializationService.findAll());

        model.addAttribute("requestUpdateCandidate", updateCandidateDTO);


        return "candidates/edit_candidate_page";
    }


}
