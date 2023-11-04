package com.example.cvservice.service.Direction;

import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.main.Direction;

public class UpdateDirectionData {

    public Direction updateDirection(Direction direction, UpdateDirectionDTO updateDirectionDTO) {

        if (updateDirectionDTO.getName() != null && !updateDirectionDTO.getName().trim().isEmpty()) {
            direction.setName(updateDirectionDTO.getName());
        }

        if (updateDirectionDTO.getDescription() != null) {
            direction.setDescription(updateDirectionDTO.getDescription());
        }
        return direction;
    }

}
