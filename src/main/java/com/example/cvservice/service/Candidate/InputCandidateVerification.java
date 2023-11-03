package com.example.cvservice.service.Candidate;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;

public class InputCandidateVerification {
    public boolean doesCandidateIsEmpty(NewCandidateDTO candidateDTO) {
        if (candidateDTO.getName() == null || candidateDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (candidateDTO.getSecond_name() == null || candidateDTO.getSecond_name().trim().isEmpty()) {
            return true;
        }

        if (candidateDTO.getPatr() == null || candidateDTO.getPatr().trim().isEmpty()) {
            return true;

        }
        if (candidateDTO.getDirections() == null || candidateDTO.getDirections().size() == 0) {
            return true;
        }
        return false;
    }

    public boolean doesUpdatedCandidateIsEmpty(UpdateCandidateDTO updateCandidateDTO) {
        if (updateCandidateDTO.getName() == null || updateCandidateDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (updateCandidateDTO.getSecond_name() == null || updateCandidateDTO.getSecond_name().trim().isEmpty()) {
            return true;
        }

        if (updateCandidateDTO.getPatr() == null  || updateCandidateDTO.getPatr().trim().isEmpty()) {
            return true;

        }
        if (updateCandidateDTO.getDirections() == null || updateCandidateDTO.getDirections().size() == 0) {
            return true;
        }
        return false;
    }

}
