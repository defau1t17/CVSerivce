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
@RequestMapping("/v1/directions")
@Tag(name = "Directions", description = "API FOR MANAGING DIRECTIONS")
public class DirectionRestController {

    @Autowired
    private DirectionService directionService;

    @Operation(summary = "Add new Direction")
    @ApiResponse(responseCode = "200", description = "New Direction has been added")
    @ApiResponse(responseCode = "409", description = "Error while adding new Direction. Direction exists or DTO empty")
    @PostMapping("/")
    public ResponseEntity<String> addNewDirection(@ModelAttribute NewDirectionDTO newDirectionDTO) {
        if (!new InputDirectionVerification().isDirectionEmpty(newDirectionDTO) && !new InputDirectionVerification().isDirectionExists(newDirectionDTO, directionService)) {
            directionService.save(new Direction(newDirectionDTO.getName(), newDirectionDTO.getDescription()));
            return ResponseEntity.ok("New Direction has been added");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error while adding new Direction. Direction exists or DTO empty");
    }

    @Operation(summary = "Update Direction by ID")
    @ApiResponse(responseCode = "200", description = "Direction has been updated")
    @ApiResponse(responseCode = "404", description = "Direction with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<String> addNewDirection(@PathVariable(value = "id") Long id, @ModelAttribute UpdateDirectionDTO updateDirectionDTO) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        if (optionalDirection.isPresent()) {
            directionService.update(new UpdateDirectionData().updateDirection(optionalDirection.get(), updateDirectionDTO));
            return ResponseEntity.ok("Direction has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Direction with such ID not found");
        }
    }

    @Operation(summary = "Get Direction ID")
    @ApiResponse(responseCode = "200", description = "Direction's Data")
    @ApiResponse(responseCode = "404", description = "Direction with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<Direction> getDirectionByID(@PathVariable(value = "id") Long id) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        return optionalDirection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Directions by params")
    @ApiResponse(responseCode = "200", description = "Directions Data")
    @GetMapping("")
    public ResponseEntity<List<Direction>> getDirectionsByParams(@RequestParam(required = false) Optional<Integer> page,
                                                                 @RequestParam(required = false) Optional<Integer> size,
                                                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                                                 @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                 @RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String description) {
        return ResponseEntity.ok(directionService.findDirectionsByParams(page.orElse(0), size.orElse(10), name, description, sort, direction).getContent());
    }
}
