package com.example.cvservice.Service.Candidate;

import com.example.cvservice.DTO.Candidate.UpdateCandidateDTO;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Service.Files.FileService;

public class UpdateCandidateData {

    public Candidate updateCandidate(Candidate candidate, UpdateCandidateDTO updateCandidateDTO) {
        if (!updateCandidateDTO.getName().trim().isEmpty() || updateCandidateDTO.getName() != null) {
            candidate.setName(updateCandidateDTO.getName());
        }
        if (!updateCandidateDTO.getSecond_name().trim().isEmpty() || updateCandidateDTO.getSecond_name() != null) {
            candidate.setSecondName(updateCandidateDTO.getSecond_name());
        }
        if (!updateCandidateDTO.getPatr().trim().isEmpty() || updateCandidateDTO.getPatr() != null) {
            candidate.setPatronymic(updateCandidateDTO.getPatr());
        }
        if (updateCandidateDTO.getCandidateDesc() != null) {
            candidate.setCandidateDescription(updateCandidateDTO.getCandidateDesc());
        }
        if (!updateCandidateDTO.getCvFile().isEmpty()) {
            candidate.setCurriculumVitae(FileService.buildCV(updateCandidateDTO.getCvFile()));
        }
        if (!updateCandidateDTO.getImageFile().isEmpty()) {
            candidate.setImage(FileService.buildImage(updateCandidateDTO.getImageFile()));
        }
        if (updateCandidateDTO.getDirections().size() != 0) {
            candidate.setDirections(updateCandidateDTO.getDirections());
        }
        return candidate;
    }
}
