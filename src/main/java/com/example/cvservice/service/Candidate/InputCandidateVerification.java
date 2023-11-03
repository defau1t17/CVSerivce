package com.example.cvservice.service.Candidate;

import com.example.cvservice.dto.Candidate.NewCandidateDTO;
import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;

public class InputCandidateVerification {
    public static boolean doesCandidateIsEmpty(NewCandidateDTO candidateDTO) {
        if (candidateDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (candidateDTO.getSecond_name().trim().isEmpty()) {
            return true;
        }

        if (candidateDTO.getPatr().trim().isEmpty()) {
            return true;

        }
        if (candidateDTO.getDirections() == null || candidateDTO.getDirections().size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean doesUpdatedCandidateIsEmpty(UpdateCandidateDTO UpdateCandidateDTO) {
        if (UpdateCandidateDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (UpdateCandidateDTO.getSecond_name().trim().isEmpty()) {
            return true;
        }

        if (UpdateCandidateDTO.getPatr().trim().isEmpty()) {
            return true;

        }
        if (UpdateCandidateDTO.getDirections() == null || UpdateCandidateDTO.getDirections().size() == 0) {
            return true;
        }
        return false;
    }

}
