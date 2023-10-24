package com.example.cvservice.Entity.Grade;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Embeddable
public class TestGrade {

    @Temporal(TemporalType.DATE)
    private Date date;

    private int mark;

}
