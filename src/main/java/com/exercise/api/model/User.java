package com.exercise.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private Date joinDate;
    private float weight;
    private float height;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private Date dob;
}
