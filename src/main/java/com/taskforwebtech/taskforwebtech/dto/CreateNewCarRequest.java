package com.taskforwebtech.taskforwebtech.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreateNewCarRequest {

    public static final String YEAR = "^\\d{4}$";

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank(message = "Year cannot be empty! ")
    @Pattern(regexp = YEAR,message = "Year must be a 4 digit number")
    private int year;

    @NotBlank
    private String color;

    @NotBlank
    private int horsepower;

    @NotBlank
    private String transmission;

    @NotBlank
    private int mileage;

    private String engineType;

    private int seatingCapacity;

    private String registrationNumber;

}
