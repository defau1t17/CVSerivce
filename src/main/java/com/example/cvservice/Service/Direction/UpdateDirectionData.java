package com.example.cvservice.Service.Direction;

import com.example.cvservice.DTO.Direction.UpdateDirectionDTO;
import com.example.cvservice.Entity.Main.Direction;

public class UpdateDirectionData {

    public static Direction updateDirection(Direction direction, UpdateDirectionDTO updateDirectionDTO) {

        if (!updateDirectionDTO.getName().trim().isEmpty()) {
            direction.setName(updateDirectionDTO.getName());
        }

        direction.setDescription(updateDirectionDTO.getDescription());

        return direction;
    }

}
