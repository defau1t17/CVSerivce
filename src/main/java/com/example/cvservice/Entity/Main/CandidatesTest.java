package com.example.cvservice.Entity.Main;


import com.example.cvservice.Entity.Grade.TestGrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
