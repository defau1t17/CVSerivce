package com.example.cvservice.controller.view;

import com.example.cvservice.dto.NewSpecializationDTO;
import com.example.cvservice.dto.UpdateSpecializationDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Specialization;
import com.example.cvservice.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/specializations")
public class SpecializationController {
    @Autowired
    private SpecializationService specializationService;


    @GetMapping("")
    public String displayAllSpecializationPage(@RequestParam(required = false) Optional<Integer> page,
                                               @RequestParam(required = false) Optional<Integer> size,
                                               @RequestParam(required = false, defaultValue = "name") String sort,
                                               @RequestParam(required = false, defaultValue = "ASC") String direction,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String description,
                                               Model model) {
        Page<Specialization> specializations = specializationService.findSpecializationsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), name, description, sort, direction);
        model.addAttribute("specializations", specializations);
        model.addAttribute("filterName", name);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterDesc", description);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("pages", specializations.getTotalPages());
        return "specializations/all_specializations_page";
    }

    @GetMapping("/add")
    public String displayAddNewSpecializationPage(Model model) {
        model.addAttribute("newSpecialization", new NewSpecializationDTO());
        return "specializations/add_new_specialization_page";
    }

    @GetMapping("/{id}")
    public String displaySpecializationByID(@PathVariable(value = "id") Long id, Model model) {
        Optional<Specialization> optionalSpecialization = specializationService.findSpecializationByID(id);
        Specialization specialization = null;
        if (optionalSpecialization.isPresent()) {
            specialization = optionalSpecialization.get();
        }
        model.addAttribute("specialization", specialization);

        return "specializations/specialization_page";

    }

    @GetMapping("/edit/{id}")
    public String editSpecializationByID(@PathVariable(value = "id") Long id, Model model) {
        Optional<Specialization> optionalSpecialization = specializationService.findSpecializationByID(id);
        UpdateSpecializationDTO updateSpecializationDTO = new UpdateSpecializationDTO();
        Specialization specialization = null;
        if (optionalSpecialization.isPresent()) {
            specialization = optionalSpecialization.get();
            updateSpecializationDTO.setId(specialization.getId());
            updateSpecializationDTO.setName(specialization.getName());
            updateSpecializationDTO.setDescription(specialization.getDescription());
            model.addAttribute("updateSpecialization", updateSpecializationDTO);
        } else {
            model.addAttribute("updateSpecialization", null);
        }
        return "specializations/edit_specialization_page";
    }
}
