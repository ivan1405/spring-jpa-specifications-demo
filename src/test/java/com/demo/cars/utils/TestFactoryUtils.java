package com.demo.cars.utils;

import com.demo.cars.controller.criteria.FuelType;
import com.demo.cars.dao.Car;
import com.demo.cars.dao.filter.CarsFilter;
import com.demo.cars.dao.specification.CarSpec;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class TestFactoryUtils {

    public static Specification<Car> getEmptyFilterSpec(){
        return CarSpec.filterBy(buildFilter(null, null, null, null, null));
    }

    public static Specification<Car> getFilterByMakeSpec(String make){
        return CarSpec.filterBy(buildFilter(make, null, null, null, null));
    }

    public static Specification<Car> getFilterByFuelTypeSpec(FuelType fuelType){
        return CarSpec.filterBy(buildFilter(null, fuelType, null, null, null));
    }

    public static Specification<Car> getFilterByNumberOfDoorsSpec(int numberOfDoors){
        return CarSpec.filterBy(buildFilter(null, null, numberOfDoors, null, null));
    }

    public static Specification<Car> getFilterByPriceRangeSpec(BigDecimal priceFrom, BigDecimal priceTo){
        return CarSpec.filterBy(buildFilter(null, null, null, priceFrom, priceTo));
    }

    private static CarsFilter buildFilter(String make, FuelType fuelType, Integer numberOfDoors,
                                   BigDecimal priceFrom, BigDecimal priceTo) {
        return new CarsFilter(make, fuelType, numberOfDoors, priceFrom, priceTo);
    }

    public static Car buildCar(String make, BigDecimal price, FuelType fuelType, int numberOfDoors) {
        Car car = new Car();
        car.setMake(make);
        car.setPrice(price);
        car.setFuelType(fuelType);
        car.setNumberOfDoors(numberOfDoors);
        return car;
    }
}
