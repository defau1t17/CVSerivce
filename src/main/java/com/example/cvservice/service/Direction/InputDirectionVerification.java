package com.example.cvservice.service.Direction;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;

public class InputDirectionVerification {

    public  boolean isDirectionEmpty(NewDirectionDTO newDirectionDTO) {
        return newDirectionDTO.getName().trim().isEmpty() && newDirectionDTO.getName() == null;
    }

    public boolean isDirectionExists(NewDirectionDTO newDirectionDTO, DirectionService directionService) {
        if (directionService.findDirectionByName(newDirectionDTO.getName()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean isUpdatedDirectionEmpty(UpdateDirectionDTO updateDirectionDTO) {
        return updateDirectionDTO.getName() == null;

    }


}
