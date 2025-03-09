package com.taskforwebtech.taskforwebtech.dto;

import lombok.Builder;

@Builder
public record CarResponse(Long id,
                                String brand,
                                String model,
                                int year,
                                String color,
                                int horsepower,
                                String transmission,
                                int mileage,
                                String engineType,
                                int seatingCapacity,
                                String registrationNumber)
{}
