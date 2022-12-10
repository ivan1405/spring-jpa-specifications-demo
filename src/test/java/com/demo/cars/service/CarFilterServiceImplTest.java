package com.demo.cars.service;

import com.demo.cars.controller.criteria.FuelType;
import com.demo.cars.dao.Car;
import com.demo.cars.dao.filter.CarsFilter;
import com.demo.cars.dao.repository.CarRepository;
import com.demo.cars.mapper.CarMapperImpl;
import com.demo.cars.model.CarDto;
import com.demo.cars.model.CarsPageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static com.demo.cars.utils.TestFactoryUtils.buildCar;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarFilterServiceImplTest {

    @Mock
    private CarRepository carRepository;

    private CarFilterServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CarFilterServiceImpl(carRepository, new CarMapperImpl());
    }

    @Test
    void givenFilterIsSet_whenSearchCarsPageResponse_thenCarsAreReturned() {
        when(carRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(getCarPage());

        // It doesn't matter what values we use to configure the filter, it's mocked
        CarsPageResponse<CarDto> cars = underTest.searchCars(new CarsFilter("make", null, 0, BigDecimal.ZERO, BigDecimal.TEN), 0, 10);

        assertThat(cars).isNotEmpty();
        assertThat(cars.getSize()).isEqualTo(3);
        assertThat(cars.getTotalSize()).isEqualTo(3);
        assertThat(cars.getContent().get(0).getMake()).isEqualTo("audi");
        assertThat(cars.getContent().get(1).getMake()).isEqualTo("ferrari");
        assertThat(cars.getContent().get(2).getMake()).isEqualTo("volkswagen");
    }

    private Page<Car> getCarPage() {
        List<Car> content = List.of(buildCar("audi", new BigDecimal("30000"), FuelType.GASOLINE, 4), buildCar("ferrari", new BigDecimal("300000"), FuelType.GASOLINE, 2), buildCar("volkswagen", new BigDecimal("25000"), FuelType.DIESEL, 4));
        return new PageImpl<>(content);
    }
}