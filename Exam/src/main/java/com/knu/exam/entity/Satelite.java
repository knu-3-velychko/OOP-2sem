package com.knu.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Satelite {
    private int id;
    private String name;
    private float radius;
    private float distance;
    private int planetId;
}
