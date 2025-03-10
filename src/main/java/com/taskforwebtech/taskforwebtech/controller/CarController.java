package com.taskforwebtech.taskforwebtech.controller;

import com.taskforwebtech.taskforwebtech.dto.CarResponse;
import com.taskforwebtech.taskforwebtech.dto.CreateNewCarRequest;
import com.taskforwebtech.taskforwebtech.dto.UpdateCarRequest;
import com.taskforwebtech.taskforwebtech.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars(@RequestParam(required = false) String brand,@RequestParam(required = false) String model,
                                                        @RequestParam(defaultValue = "0") int offset,@RequestParam(defaultValue = "10") int limit) {
        List<CarResponse> cars = carService.findAllCars(Optional.ofNullable(brand),Optional.ofNullable(model),offset,limit);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long carId) {
        CarResponse carResponse = carService.findById(carId);
        return ResponseEntity.ok(carResponse);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CreateNewCarRequest createCarRequest) {
        CarResponse createdCar = carService.createNewCar(createCarRequest);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @PatchMapping("/{carId}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long carId,@Valid @RequestBody UpdateCarRequest updateCarRequest) {
        CarResponse updatedCar = carService.updateCar(carId, updateCarRequest);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
