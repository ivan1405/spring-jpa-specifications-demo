package com.demo.cars.service;

import com.demo.cars.dao.filter.CarsFilter;
import com.demo.cars.model.CarsPageResponse;
import com.demo.cars.model.CarDto;

public interface CarFilterService {

    CarsPageResponse<CarDto> searchCars(CarsFilter filter, int page, int size);
}