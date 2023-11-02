package com.example.cvservice.Controller.View.Candidate;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;
import com.example.cvservice.DTO.Candidate.UpdateCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Service.Candidate.CandidateService;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.Files.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/task/candidates")
public class CandidatesController {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/view/all")
    public String allCandidatesPage(Model model,
                                    @RequestParam(required = false) Optional<Integer> page,
                                    @RequestParam(required = false) Optional<Integer> size,
                                    @RequestParam(required = false, defaultValue = "name") String sort,
                                    @RequestParam(required = false, defaultValue = "ASC") String direction,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String secondName,
                                    @RequestParam(required = false) String patronymic,
                                    @RequestParam(required = false) List<String> dir,
                                    @RequestParam(required = false) boolean filter) {
        Page<Candidate> allCandidatesByPageNumber = candidateService.findAllCandidatesByPageNumber(page.orElse(0), size.orElse(10), sort, direction, filter, name, secondName, patronymic, dir);

        model.addAttribute("allCandidates", allCandidatesByPageNumber);
        model.addAttribute("pageSize", size.orElse(10));
        model.addAttribute("pages", allCandidatesByPageNumber.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("filterName", name);
        model.addAttribute("filterSecondName", secondName);
        model.addAttribute("filterPatr", patronymic);
        model.addAttribute("filterDirections", dir);
        model.addAttribute("filter", filter);

        return "/candidates/all_candidates_page";
    }


    @GetMapping("/add")
    public String displayAddNewCandidatePage(Model model) {
        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("newCandidate", new NewCandidateDTO());
        return "/candidates/add_new_candidate_page";
    }

    @GetMapping("/candidate/{id}")
    public String displayCandidatePage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
        }
        model.addAttribute("candidate", candidate);
        return "/candidates/candidate_page";
    }

    @GetMapping("/candidate/edit/{id}")
    public String displayUpdateCandidatePage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Candidate> optionalCandidate = candidateService.findCandidateByID(id);
        Candidate candidate = null;
        UpdateCandidateDTO updateCandidateDTO = null;

        if (optionalCandidate.isPresent()) {
            candidate = optionalCandidate.get();
            if (candidate.getCurriculumVitae() != null) {
                updateCandidateDTO = UpdateCandidateDTO.builder().id(candidate.getId())
                        .name(candidate.getName())
                        .second_name(candidate.getSecondName())
                        .patr(candidate.getPatronymic())
                        .directions(candidate.getDirections())
                        .candidateDesc(candidate.getCandidateDescription())
                        .cvFile(FileService.createMultipartFileFormByteArray(candidate.getCurriculumVitae().getCvData(), "application/octet-stream", candidate.getCurriculumVitae().getCvFileName()))
                        .imageFile(FileService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            } else {
                updateCandidateDTO = UpdateCandidateDTO.builder().id(candidate.getId())
                        .name(candidate.getName())
                        .second_name(candidate.getSecondName())
                        .patr(candidate.getPatronymic())
                        .candidateDesc(candidate.getCandidateDescription())

                        .directions(candidate.getDirections())
                        .cvFile(null)
                        .imageFile(FileService.createMultipartFileFormByteArray(candidate.getImage().getImageData(), "application/octet-stream", candidate.getImage().getImageFileName())).build();
            }
        }
        model.addAttribute("allDirections", directionService.findAll());

        model.addAttribute("requestUpdateCandidate", updateCandidateDTO);


        return "/candidates/edit_candidate_page";
    }


}
