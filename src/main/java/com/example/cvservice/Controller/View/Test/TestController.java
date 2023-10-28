package com.example.cvservice.Controller.View.Test;

import com.example.cvservice.DTO.Test.NewTestDTO;
import com.example.cvservice.DTO.Test.UpdateTestDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Entity.Main.Test;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
    public String displayAllTestsPage(Model model) {
        model.addAttribute("tests", testService.findAll());
        return "/tests/all_tests_page";
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
