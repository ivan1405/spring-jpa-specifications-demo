package com.demo.cars.mapper;

import com.demo.cars.controller.criteria.CarsSearchCriteria;
import com.demo.cars.dao.Car;
import com.demo.cars.dao.filter.CarsFilter;
import com.demo.cars.model.CarDto;
import com.demo.cars.model.CarsPageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        injectionStrategy = CONSTRUCTOR
)
public interface CarMapper {

    CarsFilter toFilter(CarsSearchCriteria searchCriteria);

    CarDto toCarDto(Car car);

    List<CarDto> toCarDtos(List<Car> cars);

    default CarsPageResponse<CarDto> toCarsPageResponse(Page<Car> page) {
        CarsPageResponse<CarDto> pageResponse = new CarsPageResponse<>();
        pageResponse.setContent(toCarDtos(page.getContent()));
        pageResponse.setPage(page.getNumber());
        pageResponse.setSize(page.getSize());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setTotalSize(page.getTotalElements());
        return pageResponse;
    }

}