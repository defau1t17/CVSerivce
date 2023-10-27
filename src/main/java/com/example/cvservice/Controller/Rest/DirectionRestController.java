package com.example.cvservice.Controller.Rest;

import com.example.cvservice.DTO.Direction.NewDirectionDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.Direction.InputDirectionVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task/directions/rest")
public class DirectionRestController {

    @Autowired
    private DirectionService directionService;

    @PostMapping("/add/new")
    public ResponseEntity addNewDirection(@ModelAttribute NewDirectionDTO newDirectionDTO) {
        if (!InputDirectionVerification.isDirectionEmpty(newDirectionDTO) && !InputDirectionVerification.isDirectionExists(newDirectionDTO, directionService)) {
            directionService.save(new Direction(newDirectionDTO.getName(), newDirectionDTO.getDescription()));
            return ResponseEntity.ok().build();
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
