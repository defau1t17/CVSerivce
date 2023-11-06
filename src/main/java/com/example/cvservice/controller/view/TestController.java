package com.example.cvservice.controller.view;

import com.example.cvservice.dto.NewTestDTO;
import com.example.cvservice.dto.UpdateTestDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Test;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private TestService testService;


    @GetMapping("/add")
    public String addNewTestPage(Model model) {
        model.addAttribute("newTest", new NewTestDTO());
        model.addAttribute("specializations", specializationService.findAll());

        return "/tests/add_new_test_page";
    }

    @GetMapping("")
    public String displayAllTestsPage(@RequestParam(required = false) Optional<Integer> page,
                                      @RequestParam(required = false) Optional<Integer> size,
                                      @RequestParam(required = false, defaultValue = "name") String sort,
                                      @RequestParam(required = false, defaultValue = "ASC") String direction,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String description,
                                      @RequestParam(required = false) List<String> spec,
                                      Model model) {
        Page<Test> allTestByPageNumber = testService.findTestsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), name, description, spec, sort, direction);
        model.addAttribute("allSpecializations", specializationService.findAll());
        model.addAttribute("tests", allTestByPageNumber);
        model.addAttribute("filterSpecializations", spec);
        model.addAttribute("filterName", name);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterDesc", description);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("pages", allTestByPageNumber.getTotalPages());

        return "/tests/all_tests_page";
    }

    @GetMapping("/{id}")
    public String displayTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        Test test = null;
        if (optionalTest.isPresent()) {
            test = optionalTest.get();
        }
        model.addAttribute("test", test);
        return "/tests/test_page";
    }

    @GetMapping("/edit/{id}")
    public String displayEditTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        Test test = null;
        if (optionalTest.isPresent()) {
            test = optionalTest.get();
            model.addAttribute("updateTest", new UpdateTestDTO(test.getId(), test.getName(), test.getDescription(), test.getSpecializations()));
        } else {
            model.addAttribute("updateTest", null);
        }
        model.addAttribute("allSpecializations", specializationService.findAll());
        return "/tests/edit_test_page";

    }


}
