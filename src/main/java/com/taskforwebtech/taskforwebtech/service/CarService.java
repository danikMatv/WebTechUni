package com.taskforwebtech.taskforwebtech.service;

import com.taskforwebtech.taskforwebtech.dto.CarResponse;
import com.taskforwebtech.taskforwebtech.dto.CreateNewCarRequest;
import com.taskforwebtech.taskforwebtech.dto.UpdateCarRequest;
import com.taskforwebtech.taskforwebtech.entity.Car;
import com.taskforwebtech.taskforwebtech.exceptions.CarAlreadyExistException;
import com.taskforwebtech.taskforwebtech.exceptions.CarNotFoundException;
import com.taskforwebtech.taskforwebtech.exceptions.EmptyFilterListException;
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

        if(updateCar.getBrand() != null){
            oldCar.setBrand(updateCar.getBrand());
        }
        if(updateCar.getModel() != null){
            oldCar.setModel(updateCar.getModel());
        }
        if(updateCar.getYear() != 0){
            oldCar.setYear(updateCar.getYear());
        }
        if(updateCar.getColor() != null){
            oldCar.setColor(updateCar.getColor());
        }
        if(updateCar.getMileage() != 0){
            oldCar.setMileage(updateCar.getMileage());
        }
        if(updateCar.getHorsepower() != 0){
            oldCar.setHorsepower(updateCar.getHorsepower());
        }
        if(updateCar.getTransmission() != null){
            oldCar.setTransmission(updateCar.getTransmission());
        }
        if(updateCar.getEngineType() != null){
            oldCar.setEngineType(updateCar.getEngineType());
        }
        if(updateCar.getSeatingCapacity() != 0){
            oldCar.setSeatingCapacity(updateCar.getSeatingCapacity());
        }
        if(updateCar.getRegistrationNumber() != null){
            oldCar.setRegistrationNumber(updateCar.getRegistrationNumber());
        }

        Car newCar = carRepository.save(oldCar);

        return carMapper.mapToCarResponse(newCar);
    }

    public List<CarResponse> filterCar(List<String> filters){
        List<Car> cars = carRepository.findAll();
        switch (filters.size()){
            case 0:
                throw new EmptyFilterListException("Filter list is empty");
            case 1:
                String singleFilter = filters.get(0);
                return cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(singleFilter)
                                || car.getModel().equalsIgnoreCase(singleFilter))
                        .map(carMapper::mapToCarResponse)
                        .collect(Collectors.toList());
            case 2:
                String firstFilter = filters.get(0);
                String secondFilter = filters.get(1);
                return cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(firstFilter)
                                && car.getModel().equalsIgnoreCase(firstFilter)
                                || car.getBrand().equalsIgnoreCase(secondFilter)
                                && car.getModel().equalsIgnoreCase(secondFilter))
                        .map(carMapper::mapToCarResponse)
                        .collect(Collectors.toList());
            case 3:
                firstFilter = filters.get(0);
                secondFilter = filters.get(1);
                Integer thirdFilter = Integer.valueOf(filters.get(2));
                return cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(firstFilter)
                                && car.getModel().equalsIgnoreCase(secondFilter)
                                && car.getYear() == thirdFilter)
                        .map(carMapper::mapToCarResponse)
                        .collect(Collectors.toList());
            case 4:
                firstFilter = filters.get(0);
                secondFilter = filters.get(1);
                thirdFilter = Integer.valueOf(filters.get(2));
                String fourthFilter = filters.get(3);
                return cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(firstFilter)
                                && car.getModel().equalsIgnoreCase(secondFilter)
                                && car.getYear() == thirdFilter
                                && car.getColor().equalsIgnoreCase(fourthFilter))
                        .map(carMapper::mapToCarResponse)
                        .collect(Collectors.toList());
            case 5:
                firstFilter = filters.get(0);
                secondFilter = filters.get(1);
                thirdFilter = Integer.valueOf(filters.get(2));
                fourthFilter = filters.get(3);
                Integer fifthFilter = Integer.valueOf(filters.get(4));
                return cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(firstFilter)
                                && car.getModel().equalsIgnoreCase(secondFilter)
                                && car.getYear() == thirdFilter
                                && car.getColor().equalsIgnoreCase(fourthFilter)
                                && car.getMileage() == fifthFilter || car.getMileage() <= fifthFilter)
                        .map(carMapper::mapToCarResponse)
                        .collect(Collectors.toList());
            default:
                break;
        }
        return cars.stream()
                .map(carMapper::mapToCarResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCar(Long id) {
        Car car = carRepository.findCarById(id);
        if(car == null){
            throw new CarNotFoundException(id);
        }
        carRepository.delete(car);
    }

    public CarResponse findById(Long id) {
        Car car = carRepository.findById(id).orElseThrow (() -> new CarNotFoundException(id));
        return carMapper.mapToCarResponse(car);
    }

    public List<CarResponse> getAllCars(){
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(carMapper::mapToCarResponse)
                .collect(Collectors.toList());
    }

    public long getCountAllCars() {
        return carRepository.count();
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
