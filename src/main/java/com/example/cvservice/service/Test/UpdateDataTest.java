package com.example.cvservice.service.Test;

import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;

public class UpdateDataTest {
    public Test updateTest(Test test, UpdateTestDTO updateTestDTO) {
        if (updateTestDTO.getName() != null && !updateTestDTO.getName().trim().isEmpty()) {
            test.setName(updateTestDTO.getName());
        }
        if (updateTestDTO.getTestDirections() != null && !updateTestDTO.getTestDirections().isEmpty()) {
            test.setDirections(updateTestDTO.getTestDirections());
        }
        if (updateTestDTO.getDescription() != null) {
            test.setDescription(updateTestDTO.getDescription());
        }
        return test;
    }

}
