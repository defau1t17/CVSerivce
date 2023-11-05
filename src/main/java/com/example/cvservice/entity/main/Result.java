package com.example.cvservice.entity.main;


import com.example.cvservice.dto.Result.UpdateResultDTO;
import com.example.cvservice.entity.grade.TestGrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(unique = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(unique = false)
    private Test test;

    @Embedded
    private TestGrade grade;

    public Result update(UpdateResultDTO updateResultDTO) {
        if (updateResultDTO.getDate() != null) {
            this.getGrade().setDate(updateResultDTO.getDate());
        }

        if (updateResultDTO.getMark() >= 0 && updateResultDTO.getMark() <= 100) {
            this.getGrade().setMark(updateResultDTO.getMark());
        }

        return this;

    }
}
