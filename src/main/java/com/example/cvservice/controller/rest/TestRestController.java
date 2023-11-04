package com.example.cvservice.controller.rest;


import com.example.cvservice.dto.Test.NewTestDTO;
import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Test.InputTestValidation;
import com.example.cvservice.service.Test.TestService;
import com.example.cvservice.service.Test.UpdateDataTest;
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
@RequestMapping("/task/tests/rest")
@Tag(name = "Тесты", description = "API для управления тестами")
public class TestRestController {

    @Autowired
    private TestService testService;


    @Operation(summary = "Добавить новый тест")
    @ApiResponse(responseCode = "200", description = "Тест успешно добавлен")
    @ApiResponse(responseCode = "403", description = "Ошибка при добавлении тест. Тест уже существует или пустой")
    @PostMapping("/add")
    public ResponseEntity<String> addNewTest(@ModelAttribute NewTestDTO newTestDTO) {
        if (!new InputTestValidation().isNewTestExists(testService, newTestDTO) && !new InputTestValidation().isNewTestEmpty(newTestDTO)) {
            Test newTest = Test.builder().name(newTestDTO.getName()).description(newTestDTO.getDescription()).directions(newTestDTO.getTestDirections()).build();
            testService.save(newTest);
            return ResponseEntity.ok("Новый тест успешно добавлен");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка добавления нового теста");
    }

    @Operation(summary = "Обновить тест по ID")
    @ApiResponse(responseCode = "200", description = "Тест успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Тест по ID не найден")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateTestByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateTestDTO updateTestDTO) {
        Optional<Test> optionalTestTest = testService.findTestByID(id);
        if (optionalTestTest.isPresent()) {
            testService.update(new UpdateDataTest().updateTest(optionalTestTest.get(), updateTestDTO));
            return ResponseEntity.ok("Тест успешно обновлен");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тест с таким ID не найден");
        }
    }

    @Operation(summary = "Получить тест по ID")
    @ApiResponse(responseCode = "200", description = "Данные успешно отправлены")
    @ApiResponse(responseCode = "404", description = "Тест по ID не найден")
    @GetMapping("/get/{id}")
    public ResponseEntity<Test> getTestByID(@PathVariable(value = "id") Long id) {
        Optional<Test> optionalTest = testService.findTestByID(id);
        return optionalTest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Получить тест по параметрам")
    @ApiResponse(responseCode = "200", description = "Данные успешно отправлены")
    @GetMapping("/get")
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
