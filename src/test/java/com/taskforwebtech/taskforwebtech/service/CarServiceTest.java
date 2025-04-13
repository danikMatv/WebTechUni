package com.taskforwebtech.taskforwebtech.service;

import com.taskforwebtech.taskforwebtech.dto.CarResponse;
import com.taskforwebtech.taskforwebtech.dto.CreateNewCarRequest;
import com.taskforwebtech.taskforwebtech.entity.Car;
import com.taskforwebtech.taskforwebtech.mapper.CarMapper;
import com.taskforwebtech.taskforwebtech.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarService carService;

    @Test
    void findById_ShouldReturnCarResponse_WhenExists() {
        Long id = 1L;
        Car car = new Car();
        car.setId(id);
        car.setBrand("BMW");

        CarResponse carResponse = CarResponse.builder().id(id).brand("BMW").build();

        when(carRepository.findById(id)).thenReturn(Optional.of(car));
        when(carMapper.mapToCarResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.findById(id);

        assertNotNull(result);
        assertEquals("BMW", result.brand());
        verify(carRepository).findById(id);
        verify(carMapper).mapToCarResponse(car);
    }

    @Test
    void getAllCars_ShouldReturnListOfResponses() {
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(2L);

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));
        when(carMapper.mapToCarResponse(any(Car.class))).thenReturn(CarResponse.builder().build());

        List<CarResponse> result = carService.getAllCars();

        assertEquals(2, result.size());
        verify(carRepository).findAll();
    }
}
