package com.demo.cars.dao.repository;

import com.demo.cars.controller.criteria.FuelType;
import com.demo.cars.dao.Car;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.demo.cars.utils.TestFactoryUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired
    private CarRepository underTest;

    @Test
    void givenDataset_whenFindAllWithNoFilter_thenAllResultsAreReturned() {
        Specification<Car> spec = getEmptyFilterSpec();
        Page<Car> pageResult = underTest.findAll(spec, PageRequest.of(0, 100000));

        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getTotalElements()).isEqualTo(205);
    }

    @Test
    void givenDataset_whenFindAllByMake_thenFilterWorksAsExpected() {
        Specification<Car> spec = getFilterByMakeSpec("volkswagen");
        Page<Car> pageResult = underTest.findAll(spec, PageRequest.of(0, 100000));

        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getContent())
                .extracting(Car::getMake)
                .contains("volkswagen")
                .doesNotContain("audi", "peugeot", "nissan");
    }

    @ParameterizedTest
    @MethodSource("getFuelTypeUseCases")
    void givenDataset_whenFindAllByFuelType_thenFilterWorksAsExpected(FuelType fuelType, int expectedElements) {
        Specification<Car> spec = getFilterByFuelTypeSpec(fuelType);
        Page<Car> pageResult = underTest.findAll(spec, PageRequest.of(0, 100000));

        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getTotalElements()).isEqualTo(expectedElements);
        assertThat(pageResult.getContent())
                .hasSize(expectedElements)
                .extracting(Car::getFuelType)
                .contains(fuelType);
    }

    @Test
    void givenDataset_whenFindAllByNumberOfDoors_thenFilterWorksAsExpected() {
        Specification<Car> spec = getFilterByNumberOfDoorsSpec(2);
        Page<Car> pageResult = underTest.findAll(spec, PageRequest.of(0, 100000));

        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getTotalElements()).isEqualTo(89);
        assertThat(pageResult.getContent())
                .hasSize(89)
                .extracting(Car::getNumberOfDoors)
                .contains(2);
    }

    @Test
    void givenDataset_whenFindAllByPriceRange_thenFilterWorksAsExpected() {
        BigDecimal priceFrom = new BigDecimal("10000");
        BigDecimal priceTo = new BigDecimal("15000");
        Specification<Car> spec = getFilterByPriceRangeSpec(priceFrom, priceTo);
        Page<Car> pageResult = underTest.findAll(spec, PageRequest.of(0, 100000));

        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getTotalElements()).isEqualTo(41);
        assertThat(pageResult.getContent())
                .hasSize(41)
                .extracting(Car::getPrice)
                .have(priceBetween(priceFrom, priceTo));
    }

    private static Condition<BigDecimal> priceBetween(BigDecimal priceFrom, BigDecimal priceTo) {
        return new Condition<>(val -> val.compareTo(priceFrom) > 0 && val.compareTo(priceTo) < 0, "priceComparator");
    }

    private static Stream<Arguments> getFuelTypeUseCases() {
        return Stream.of(
                Arguments.of(FuelType.DIESEL, 20),
                Arguments.of(FuelType.GASOLINE, 185)
        );
    }

}