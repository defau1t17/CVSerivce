package com.example.cvservice.Controller.View.Test;

import com.example.cvservice.DTO.Test.NewTestDTO;
import com.example.cvservice.DTO.Test.UpdateTestDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Entity.Main.Test;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/task/tests/")
public class TestController {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private TestService testService;

    @GetMapping("/add")
    public String addNewTestPage(Model model) {
        model.addAttribute("newTest", new NewTestDTO());
        model.addAttribute("directions", directionService.findAll());
        return "/tests/add_new_test_page";
    }

    @GetMapping("/view/all")
    public String displayAllTestsPage(@RequestParam(required = false) Optional<Integer> page,
                                      @RequestParam(required = false) Optional<Integer> size,
                                      @RequestParam(required = false) boolean filter,
                                      @RequestParam(required = false, defaultValue = "name") String sort,
                                      @RequestParam(required = false, defaultValue = "ASC") String direction,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String description,
                                      @RequestParam(required = false) List<String> dir,
                                      Model model) {
        Page<Test> allTestByPageNumber = testService.findTestsByParams(page.orElse(0), size.orElse(10), filter, name, description, dir, sort, direction);
        model.addAttribute("allDirections", directionService.findAll());
        model.addAttribute("tests", allTestByPageNumber);
        model.addAttribute("filterDirections", dir);
        model.addAttribute("filterName", name);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterDesc", description);
        model.addAttribute("pageSize", size.orElse(10));
        model.addAttribute("pages", allTestByPageNumber.getTotalPages());
        return "/tests/all_tests_page";
    }

    @GetMapping("/test/{id}")
    public String displayTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        Test test = null;
        if (optionalTest.isPresent()) {
            test = optionalTest.get();
        }
        model.addAttribute("test", test);

        return "/tests/test_page";
    }


    @GetMapping("/test/edit/{id}")
    public String displayEditTestPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        Test test = null;
        if (optionalTest.isPresent()) {
            test = optionalTest.get();
            model.addAttribute("updateTest", new UpdateTestDTO(test.getId(), test.getName(), test.getDescription(), test.getDirections()));
        } else {
            model.addAttribute("updateTest", null);
        }
        model.addAttribute("allDirections", directionService.findAll());


        return "/tests/edit_test_page";

    }


}
