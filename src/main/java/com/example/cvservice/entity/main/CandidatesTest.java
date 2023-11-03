package com.example.cvservice.entity.main;


import com.example.cvservice.entity.grade.TestGrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "canditests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidatesTest {

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

}
