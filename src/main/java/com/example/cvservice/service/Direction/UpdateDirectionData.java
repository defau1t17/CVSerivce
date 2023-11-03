package com.example.cvservice.service.Direction;

import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.main.Direction;

public class UpdateDirectionData {

    public static Direction updateDirection(Direction direction, UpdateDirectionDTO updateDirectionDTO) {

        if (!updateDirectionDTO.getName().trim().isEmpty()) {
            direction.setName(updateDirectionDTO.getName());
        }

        direction.setDescription(updateDirectionDTO.getDescription());

        return direction;
    }

}
