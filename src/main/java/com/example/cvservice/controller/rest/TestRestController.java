package com.example.cvservice.controller.rest;


import com.example.cvservice.dto.Test.NewTestDTO;
import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Test.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tests")
@Tag(name = "Tests", description = "API FOR MANAGING TESTS")
public class TestRestController {
    @Autowired
    private TestService testService;

    @Operation(summary = "Add new Test")
    @ApiResponse(responseCode = "200", description = "New Test has been added")
    @ApiResponse(responseCode = "403", description = "Error while adding new Test. Test exists or DTO empty")
    @PostMapping("/")
    public ResponseEntity<?> addNewTest(@ModelAttribute NewTestDTO newTestDTO) {
        Test test = testService.saveNewTest(newTestDTO);
        return ResponseEntity.ok(test);
    }

    @Operation(summary = "Update Test by ID")
    @ApiResponse(responseCode = "200", description = "Test has been updated")
    @ApiResponse(responseCode = "404", description = "Test with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateTestDTO updateTestDTO) {
        Test test = testService.updateTest(id, updateTestDTO);
        return ResponseEntity.ok(test);
    }

    @Operation(summary = "Get Test by ID")
    @ApiResponse(responseCode = "200", description = "Test's data")
    @ApiResponse(responseCode = "404", description = "Test with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestByID(@PathVariable(value = "id") Long id) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        return optionalTest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get Tests by params")
    @ApiResponse(responseCode = "200", description = "Tests Data")
    @GetMapping("")
    public ResponseEntity<List<Test>> getTestsByParams(@RequestParam(required = false) Optional<Integer> page,
                                                       @RequestParam(required = false) Optional<Integer> size,
                                                       @RequestParam(required = false, defaultValue = "name") String sort,
                                                       @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String description,
                                                       @RequestParam(required = false) List<String> dir) {
        return ResponseEntity.ok(testService.findTestsByParams(page.orElse(0), size.orElse(10), name, description, dir, sort, direction).getContent());
    }

}
