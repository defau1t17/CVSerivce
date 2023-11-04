package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Direction.DirectionService;
import com.example.cvservice.service.Direction.InputDirectionVerification;
import com.example.cvservice.service.Direction.UpdateDirectionData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task/directions/rest")
@Tag(name = "Направления", description = "API для управления направлениями")
public class DirectionRestController {

    @Autowired
    private DirectionService directionService;

    @Operation(summary = "Добавить новое направление")
    @ApiResponse(responseCode = "200", description = "Направление успешно добавлено")
    @ApiResponse(responseCode = "409", description = "Ошибка при добавлении направления. Направление уже существует или пустое")
    @PostMapping("/add")
    public ResponseEntity<String> addNewDirection(@ModelAttribute NewDirectionDTO newDirectionDTO) {
        if (!new InputDirectionVerification().isDirectionEmpty(newDirectionDTO) && !new InputDirectionVerification().isDirectionExists(newDirectionDTO, directionService)) {
            directionService.save(new Direction(newDirectionDTO.getName(), newDirectionDTO.getDescription()));
            return ResponseEntity.ok("Новое направление успешно добавлено");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка добавления нового направления");
    }

    @Operation(summary = "Обновить направление по ID")
    @ApiResponse(responseCode = "200", description = "Направление успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Напревление с таким ID не найдено")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> addNewDirection(@PathVariable(value = "id") Long id, @ModelAttribute UpdateDirectionDTO updateDirectionDTO) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        if (optionalDirection.isPresent()) {
            directionService.update(new UpdateDirectionData().updateDirection(optionalDirection.get(), updateDirectionDTO));
            return ResponseEntity.ok("Направление успешно обновленно");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка обновления нарпавления");
        }
    }

    @Operation(summary = "Получить направление по ID")
    @ApiResponse(responseCode = "200", description = "Данные направления")
    @ApiResponse(responseCode = "404", description = "Напревление с таким ID не найдено")
    @GetMapping("/get/{id}")
    public ResponseEntity<Direction> getDirectionByID(@PathVariable(value = "id") Long id) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        return optionalDirection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновить направление по параметрам")
    @ApiResponse(responseCode = "200", description = "Данные направления")
    @GetMapping("/get")
    public ResponseEntity<List<Direction>> getDirectionsByParams(@RequestParam(required = false) Optional<Integer> page,
                                                                 @RequestParam(required = false) Optional<Integer> size,
                                                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                                                 @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                 @RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String description) {
        return ResponseEntity.ok(directionService.findDirectionsByParams(page.orElse(0), size.orElse(10), name, description, sort, direction).getContent());
    }
}
