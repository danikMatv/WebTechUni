package com.taskforwebtech.taskforwebtech.repository;

import com.taskforwebtech.taskforwebtech.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findCarByBrandOrModel(String brand, String model);
}
