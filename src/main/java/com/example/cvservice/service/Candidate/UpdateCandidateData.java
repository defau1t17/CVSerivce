package com.example.cvservice.service.Candidate;

import com.example.cvservice.dto.Candidate.UpdateCandidateDTO;
import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.service.Files.FileService;

public class UpdateCandidateData {

    public Candidate updateCandidate(Candidate candidate, UpdateCandidateDTO updateCandidateDTO) {
        if (updateCandidateDTO.getName() != null && !updateCandidateDTO.getName().trim().isEmpty()) {
            candidate.setName(updateCandidateDTO.getName());
        }
        if (updateCandidateDTO.getSecond_name() != null && !updateCandidateDTO.getSecond_name().trim().isEmpty()) {
            candidate.setSecondName(updateCandidateDTO.getSecond_name());
        }
        if (updateCandidateDTO.getPatr() != null && !updateCandidateDTO.getPatr().trim().isEmpty()) {
            candidate.setPatronymic(updateCandidateDTO.getPatr());
        }
        if (updateCandidateDTO.getCandidateDesc() != null) {
            candidate.setCandidateDescription(updateCandidateDTO.getCandidateDesc());
        }
        if (updateCandidateDTO.getCvFile() != null && !updateCandidateDTO.getCvFile().isEmpty()) {
            candidate.setCurriculumVitae(FileService.buildCV(updateCandidateDTO.getCvFile()));
        }
        if (updateCandidateDTO.getImageFile() != null && !updateCandidateDTO.getImageFile().isEmpty()) {
            candidate.setImage(FileService.buildImage(updateCandidateDTO.getImageFile()));
        }
        if (updateCandidateDTO.getDirections() != null && updateCandidateDTO.getDirections().size() != 0) {
            candidate.setDirections(updateCandidateDTO.getDirections());
        }
        return candidate;
    }
}
