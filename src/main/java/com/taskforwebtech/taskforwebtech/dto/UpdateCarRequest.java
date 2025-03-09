package com.taskforwebtech.taskforwebtech.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCarRequest {

    private String brand;
    private String model;
    private int year;
    private String color;
    private int horsepower;
    private String transmission;
    private int mileage;
    private String engineType;
    private int seatingCapacity;
    private String registrationNumber;

}
