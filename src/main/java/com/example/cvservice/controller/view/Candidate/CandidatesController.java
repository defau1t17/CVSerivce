package com.example.cvservice.controller.view.Candidate;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Direction.DirectionService;
import com.example.cvservice.service.Files.CVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private DirectionService directionService;

    @Autowired
    private CandidateService candidateService;

    Logger logger = LoggerFactory.getLogger(CandidatesController.class);


    @GetMapping("")
    public String allCandidatesPage(Model model,
                                    @RequestParam(required = false) Optional<Integer> page,
                                    @RequestParam(required = false) Optional<Integer> size,
                                    @RequestParam(required = false, defaultValue = "name") String sort,
                                    @RequestParam(required = false, defaultValue = "ASC") String direction,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String secondName,
                                    @RequestParam(required = false) String patronymic,
                                    @RequestParam(required = false) List<String> dir) {
        Page<Candidate> allCandidatesByPageNumber = candidateService.findAllCandidatesByPageNumber(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), sort, direction, name, secondName, patronymic, dir);
        model.addAttribute("allCandidates", allCandidatesByPageNumber);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("pages", allCandidatesByPageNumber.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("filterName", name);
        model.addAttribute("filterSecondName", secondName);
        model.addAttribute("filterPatr", patronymic);
        model.addAttribute("filterDirections", dir);
        logger.info("All candidates page works ");

        return "/candidates/all_candidates_page";
    }


    @GetMapping("/add")
    public String displayAddNewCandidatePage(Model model) {
        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("newCandidate", new NewCandidateDTO());
        logger.info("Add new candidate page works ");

        return "/candidates/add_new_candidate_page";
    }

    @GetMapping("/{id}")
    public String displayCandidatePage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
        }
        model.addAttribute("candidate", candidate);
        logger.info("candidate by id page works");

        return "/candidates/candidate_page";
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
                        .directions(candidate.getDirections())
                        .candidateDesc(candidate.getCandidateDescription())
                        .cvFile(CVService.createMultipartFileFormByteArray(candidate.getCv().getCvData(), "application/octet-stream", candidate.getCv().getCvFileName()))
                        .imageFile(CVService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            } else {
                updateCandidateDTO = UpdateCandidateDTO.builder().id(candidate.getId())
                        .name(candidate.getName())
                        .second_name(candidate.getSecondName())
                        .patr(candidate.getPatronymic())
                        .candidateDesc(candidate.getCandidateDescription())

                        .directions(candidate.getDirections())
                        .cvFile(null)
                        .imageFile(CVService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            }
            logger.info("entity for edit has been created");
        }
        model.addAttribute("allDirections", directionService.findAll());

        model.addAttribute("requestUpdateCandidate", updateCandidateDTO);

        logger.info("edit candidate page works ");


        return "/candidates/edit_candidate_page";
    }


}
