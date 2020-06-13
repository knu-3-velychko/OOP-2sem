package com.knu.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Planet {
    private int id;
    private String name;
    private float radius;
    private float temperature;
    private boolean hasLife;
    private int galaxyId;
}
