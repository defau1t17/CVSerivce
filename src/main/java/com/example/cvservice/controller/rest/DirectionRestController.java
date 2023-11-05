package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Direction.DirectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/directions")
@Tag(name = "Directions", description = "API FOR MANAGING DIRECTIONS")
public class DirectionRestController {

    @Autowired
    private DirectionService directionService;

    @Operation(summary = "Add new Direction")
    @ApiResponse(responseCode = "200", description = "New Direction has been added")
    @ApiResponse(responseCode = "409", description = "Error while adding new Direction. Direction exists or DTO empty")
    @PostMapping("/")
    public ResponseEntity<?> addNewDirection(@ModelAttribute NewDirectionDTO newDirectionDTO) {
        Direction direction = directionService.saveNewDirection(newDirectionDTO);
        return ResponseEntity.ok().body(direction);
    }

    @Operation(summary = "Update Direction by ID")
    @ApiResponse(responseCode = "200", description = "Direction has been updated")
    @ApiResponse(responseCode = "404", description = "Direction with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDirectionByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateDirectionDTO updateDirectionDTO) {
        Direction direction = directionService.updateDirection(id, updateDirectionDTO);
        return ResponseEntity.ok(direction);
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
        return ResponseEntity.ok(directionService.findDirectionsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), name, description, sort, direction).getContent());
    }
}
