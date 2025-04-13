package com.taskforwebtech.taskforwebtech.repository;

import com.taskforwebtech.taskforwebtech.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/V2_Insert_test_data.sql")
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void findById_ShouldReturnCar_WhenExists() {
        Long id = 1L;
        Optional<Car> optionalCar = carRepository.findById(id);

        assertTrue(optionalCar.isPresent());
        assertEquals("BMW", optionalCar.get().getBrand());
    }

    @Test
    public void findByBrandOrModel_ShouldReturnCar() {
        Car result = carRepository.findCarByBrandOrModel("Tesla", "Model S");

        assertNotNull(result);
        assertEquals("Tesla", result.getBrand());
    }
}
