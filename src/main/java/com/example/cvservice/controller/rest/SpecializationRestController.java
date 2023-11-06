package com.example.cvservice.controller.rest;

import com.example.cvservice.dto.NewSpecializationDTO;
import com.example.cvservice.dto.UpdateSpecializationDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.Specialization;
import com.example.cvservice.service.SpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/specializations")
@Tag(name = "Specializations", description = "API FOR MANAGING SPECIALIZATIONS")
public class SpecializationRestController {

    @Autowired
    private SpecializationService specializationService;

    Logger logger = LoggerFactory.getLogger(SpecializationRestController.class);


    @Operation(summary = "Add new Specialization")
    @ApiResponse(responseCode = "200", description = "New Specialization has been added")
    @ApiResponse(responseCode = "409", description = "Error while adding new Specialization. Specialization exists or DTO empty")
    @PostMapping("/")
    public ResponseEntity<?> addNewSpecialization(@ModelAttribute NewSpecializationDTO newSpecializationDTO) {
        Specialization specialization = specializationService.saveNewSpecialization(newSpecializationDTO);
        logger.info("направление с [id: " + specialization.getId() + " ] создано");
        return ResponseEntity.ok().body(specialization);
    }

    @Operation(summary = "Update Specialization by ID")
    @ApiResponse(responseCode = "200", description = "Specialization has been updated")
    @ApiResponse(responseCode = "404", description = "Specialization with such ID not found")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSpecializationByID(@PathVariable(value = "id") Long id, @ModelAttribute UpdateSpecializationDTO updateSpecializationDTO) {
        Specialization specialization = specializationService.updateSpecialization(id, updateSpecializationDTO);
        logger.info("направление с [id: " + specialization.getId() + " ] обновленно");
        return ResponseEntity.ok(specialization);
    }

    @Operation(summary = "Get Specialization ID")
    @ApiResponse(responseCode = "200", description = "Specialization's Data")
    @ApiResponse(responseCode = "404", description = "Specialization with such ID not found")
    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationByID(@PathVariable(value = "id") Long id) {
        Optional<Specialization> optionalSpecialization = specializationService.findSpecializationByID(id);
        return optionalSpecialization.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Specializations by params")
    @ApiResponse(responseCode = "200", description = "Specializations Data")
    @GetMapping("")
    public ResponseEntity<List<Specialization>> getSpecializationByParams(@RequestParam(required = false) Optional<Integer> page,
                                                                          @RequestParam(required = false) Optional<Integer> size,
                                                                          @RequestParam(required = false, defaultValue = "name") String sort,
                                                                          @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                          @RequestParam(required = false) String name,
                                                                          @RequestParam(required = false) String description) {
        return ResponseEntity.ok(specializationService.findSpecializationsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), name, description, sort, direction).getContent());
    }
}
