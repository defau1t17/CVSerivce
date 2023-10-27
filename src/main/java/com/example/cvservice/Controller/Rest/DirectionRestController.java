package com.example.cvservice.Controller.Rest;

import com.example.cvservice.DTO.Direction.NewDirectionDTO;
import com.example.cvservice.DTO.Direction.UpdateDirectionDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Direction.DirectionService;
import com.example.cvservice.Service.Direction.InputDirectionVerification;
import com.example.cvservice.Service.Direction.UpdateDirectionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PatchMapping("/direction/update/{id}")
    public ResponseEntity addNewDirection(@PathVariable(value = "id") Long id, @ModelAttribute UpdateDirectionDTO updateDirectionDTO) {
        if (!InputDirectionVerification.isUpdatedDirectionEmpty(updateDirectionDTO)) {
            Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
            if (optionalDirection.isPresent()) {
                directionService.update(UpdateDirectionData.updateDirection(optionalDirection.get(), updateDirectionDTO));
                return ResponseEntity.ok().build();
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/direction/delete/{id}")
    public ResponseEntity removeDirectionByID(@PathVariable(value = "id") Long id) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        optionalDirection.ifPresent(direction -> directionService.delete(direction));
        return ResponseEntity.ok().build();
    }


}
