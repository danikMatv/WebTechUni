package com.taskforwebtech.taskforwebtech.controller;

import com.taskforwebtech.taskforwebtech.dto.CarResponse;
import com.taskforwebtech.taskforwebtech.dto.CreateNewCarRequest;
import com.taskforwebtech.taskforwebtech.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void getCarById_ShouldReturnOkWithJson() throws Exception {
        Long id = 1L;
        CarResponse response = CarResponse.builder()
                .id(id).brand("BMW").model("X5").year(2020).color("Black")
                .horsepower(300).transmission("Automatic")
                .mileage(20000).engineType("Petrol")
                .seatingCapacity(5).registrationNumber("AA1234BB")
                .build();

        when(carService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/cars/{carId}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X5"));

        verify(carService).findById(id);
    }

    @Test
    public void createCar_ShouldReturnCreated() throws Exception {
        CreateNewCarRequest request = CreateNewCarRequest.builder()
                .brand("Audi").model("A4").year(2023).color("Gray")
                .horsepower(220).transmission("Automatic")
                .mileage(10000).engineType("Petrol")
                .seatingCapacity(5).registrationNumber("AA4321BB")
                .build();

        CarResponse response = CarResponse.builder()
                .id(99L).brand("Audi").model("A4").year(2023).color("Gray")
                .horsepower(220).transmission("Automatic")
                .mileage(10000).engineType("Petrol")
                .seatingCapacity(5).registrationNumber("AA4321BB")
                .build();

        when(carService.createNewCar(request)).thenReturn(response);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Audi"))
                .andExpect(jsonPath("$.model").value("A4"));

        verify(carService).createNewCar(request);
    }

    @Test
    public void getAllCars_ShouldReturnList() throws Exception {
        CarResponse car1 = CarResponse.builder().id(1L).brand("BMW").model("X5").build();
        CarResponse car2 = CarResponse.builder().id(2L).brand("Tesla").model("Model S").build();

        when(carService.getAllCars()).thenReturn(List.of(car1, car2));

        mockMvc.perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("BMW"))
                .andExpect(jsonPath("$[1].brand").value("Tesla"));

        verify(carService).getAllCars();
    }
}