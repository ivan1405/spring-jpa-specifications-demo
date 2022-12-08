package com.demo.cars.dao.filter;

import com.demo.cars.controller.criteria.FuelType;

import java.math.BigDecimal;

public record CarsFilter(String make, FuelType fuelType, Integer numberOfDoors, BigDecimal priceFrom, BigDecimal priceTo) {
}