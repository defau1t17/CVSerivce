package com.example.cvservice.service.Test;

import com.example.cvservice.dto.Test.NewTestDTO;
import com.example.cvservice.dto.Test.UpdateTestDTO;
import com.example.cvservice.entity.main.Test;

import java.util.Optional;

public class InputTestValidation {

    public boolean isNewTestEmpty(NewTestDTO newTestDTO) {
        if (newTestDTO.getName() == null || newTestDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (newTestDTO.getTestDirections() == null || newTestDTO.getTestDirections().size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isNewTestExists(TestService testService, NewTestDTO newTestDTO) {
        Optional<Test> optionalTest = testService.findTestByName(newTestDTO.getName());
        return optionalTest.isPresent();
    }



}
