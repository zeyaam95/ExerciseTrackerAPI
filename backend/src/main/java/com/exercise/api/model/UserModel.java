package com.exercise.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String userName;
    private float weight;
    private float height;
    private int age;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private Date joinDate;
}
