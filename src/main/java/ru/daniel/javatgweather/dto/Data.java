package ru.daniel.javatgweather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    private double windSpeed;
    private int temp;
    private int pop;
    private Weather weather;
}
