package com.example.cvservice.service.Test;

import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;

public class UpdateDataTest {
    public Test updateTest(Test test, UpdateTestDTO updateTestDTO) {
        if (!updateTestDTO.getName().trim().isEmpty()) {
            test.setName(updateTestDTO.getName());
        }
        if (updateTestDTO.getTestDirections().size() != 0) {
            test.setDirections(updateTestDTO.getTestDirections());
        }

        test.setDescription(updateTestDTO.getDescription());
        return test;
    }

}
