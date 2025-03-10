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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Transactional
    public CarResponse createNewCar(CreateNewCarRequest createCar) {
        checkWhetherSuchCarIsExist(createCar);
        Car car = carMapper.map(createCar);

        return carMapper.mapToCarResponse(carRepository.save(car));
    }

    @Transactional
    public CarResponse updateCar(Long carId, UpdateCarRequest updateCar) {
        Car oldCar = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
        checkWhetherCarExists(updateCar);
        Car newCar = carRepository.save(carMapper.updateCar(oldCar, updateCar));

        return carMapper.mapToCarResponse(newCar);
    }

    @Transactional
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        carRepository.delete(car);
    }

    public CarResponse findById(Long id) {
        Car car = carRepository.findById(id).orElseThrow (() -> new CarNotFoundException(id));
        return carMapper.mapToCarResponse(car);
    }

    //TODO: refactor this trash
    public List<CarResponse> findAllCars(Optional<String> brand,
                                         Optional<String> model,
                                         int offset,
                                         int limit) {
        List<Car> allCars = carRepository.findAll();
        if (brand.isPresent() && !brand.get().isBlank()) {
            allCars = allCars.stream()
                    .filter(car -> car.getBrand().equalsIgnoreCase(brand.get()))
                    .collect(Collectors.toList());
        }
        if (model.isPresent() && !model.get().isBlank()) {
            allCars = allCars.stream()
                    .filter(car -> car.getModel().equalsIgnoreCase(model.get()))
                    .collect(Collectors.toList());
        }

        if (offset >= allCars.size()) {
            return Collections.emptyList();
        }
        int endIndex = Math.min(offset + limit, allCars.size());
        List<Car> page = allCars.subList(offset, endIndex);

        return page.stream()
                .map(carMapper::mapToCarResponse)
                .collect(Collectors.toList());
    }

    private Boolean checkWhetherCarExists(UpdateCarRequest updateCarRequest) {
        Car checkCar = carRepository.findCarByBrandOrModel(updateCarRequest.getBrand(), updateCarRequest.getModel());
        if(checkCar != null){
            throw new CarAlreadyExistException(updateCarRequest.getModel(),"Car already exists");
        }
        return false;
    }
    private Boolean checkWhetherSuchCarIsExist(CreateNewCarRequest createCar) {
        Car checkCar = carRepository.findCarByBrandOrModel(createCar.getBrand(), createCar.getModel());
        if(checkCar != null){
            throw new CarAlreadyExistException(createCar.getModel(),"Car already exists");
        }
        return false;
    }
}
