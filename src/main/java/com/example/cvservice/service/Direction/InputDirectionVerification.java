package com.example.cvservice.service.Direction;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;

public class InputDirectionVerification {

    public static boolean isDirectionEmpty(NewDirectionDTO newDirectionDTO) {
        return newDirectionDTO.getName().trim().isEmpty() && newDirectionDTO.getName() == null;
    }

    public static boolean isDirectionExists(NewDirectionDTO newDirectionDTO, DirectionService directionService) {
        if (directionService.findDirectionByName(newDirectionDTO.getName()).isPresent()) {
            return true;
        }
        return false;
    }

    public static boolean isUpdatedDirectionEmpty(UpdateDirectionDTO updateDirectionDTO) {
        return updateDirectionDTO.getName().trim().isEmpty() && updateDirectionDTO.getName() == null;

    }


}
