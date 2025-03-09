package com.taskforwebtech.taskforwebtech.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int year;
    private String color;
    private int horsepower;
    private String transmission;
    private int mileage;

    @Column(name = "engine_type")
    private String engineType;
    @Column(name = "seating_capacity")
    private int seatingCapacity;
    @Column(name = "registration_number")
    private String registrationNumber;

}
