package com.taskforwebtech.taskforwebtech.service;

import com.taskforwebtech.taskforwebtech.dto.CarResponse;
import com.taskforwebtech.taskforwebtech.dto.CreateNewCarRequest;
import com.taskforwebtech.taskforwebtech.dto.UpdateCarRequest;
import com.taskforwebtech.taskforwebtech.entity.Car;
import com.taskforwebtech.taskforwebtech.exceptions.CarAlreadyExistException;
import com.taskforwebtech.taskforwebtech.exceptions.CarNotFoundException;
import com.taskforwebtech.taskforwebtech.mapper.CarMapper;
import com.taskforwebtech.taskforwebtech.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Transactional
    public CarResponse createNewCar(CreateNewCarRequest createCar) {
        checkWhetherCarExists(createCar);
        Car car = carMapper.map(createCar);

        return carMapper.mapToCarResponse(carRepository.save(car));
    }

    @Transactional
    public CarResponse updateCar(Long carId, UpdateCarRequest updateCar) {
        Car oldCar = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
        Car newCar = carRepository.save(carMapper.updateCar(oldCar, updateCar));

        return carMapper.mapToCarResponse(newCar);
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow (() -> new CarNotFoundException(id));
    }

    private Boolean checkWhetherCarExists(CreateNewCarRequest createCar) {
        Car checkCar = carRepository.findCarByBrandOrModel(createCar.getBrand(), createCar.getModel());
        if(checkCar != null){
            throw new CarAlreadyExistException(createCar.getModel(),"Car already exists");
        }
    }
}
