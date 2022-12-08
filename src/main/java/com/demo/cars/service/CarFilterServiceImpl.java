package com.demo.cars.service;

import com.demo.cars.dao.Car;
import com.demo.cars.dao.filter.CarsFilter;
import com.demo.cars.dao.repository.CarRepository;
import com.demo.cars.dao.specification.CarSpec;
import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.CarDto;
import com.demo.cars.model.CarsPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CarFilterServiceImpl implements CarFilterService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarFilterServiceImpl(CarRepository carRepository,
                                CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public CarsPageResponse<CarDto> searchCars(CarsFilter filter, int page, int size) {
        Specification<Car> spec = CarSpec.filterBy(filter);
        Page<Car> pageResult = carRepository.findAll(spec, PageRequest.of(page, size));
        return carMapper.toCarsPageResponse(pageResult);
    }
}