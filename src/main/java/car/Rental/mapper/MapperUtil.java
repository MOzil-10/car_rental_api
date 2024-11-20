package car.Rental.mapper;

import car.Rental.dto.CarDto;
import car.Rental.dto.RentalRequestDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {

    public static RentalRequestDto toRentalRequestDto(RentalRequest rentalRequest) {
        RentalRequestDto dto = new RentalRequestDto();
        dto.setId(rentalRequest.getId());
        dto.setUserId(rentalRequest.getUser().getId());
        dto.setCarId(rentalRequest.getCar().getId());
        dto.setCarMake(rentalRequest.getCar().getMake());
        dto.setCarModel(rentalRequest.getCar().getModel());
        dto.setStartDate(rentalRequest.getStartDate());
        dto.setEndDate(rentalRequest.getEndDate());
        dto.setStatus(rentalRequest.getStatus());
        dto.setRejectionReason(rentalRequest.getRejectionReason());
        return dto;
    }

    public static List<RentalRequestDto> toRentalRequestDtoList(List<RentalRequest> rentalRequests) {
        return rentalRequests.stream()
                .map(MapperUtil::toRentalRequestDto)
                .collect(Collectors.toList());
    }

    public static CarDto toCarDto(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setAvailable(car.isAvailable());
        dto.setRentalRate(car.getRentalRate());
        return dto;
    }

    public static Car toCarEntity(CarDto carDto) {
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setAvailable(carDto.isAvailable());
        car.setRentalRate(carDto.getRentalRate());
        return car;
    }

    public static List<CarDto> toCarDtoList(List<Car> cars) {
        return cars.stream()
                .map(MapperUtil::toCarDto)
                .collect(Collectors.toList());
    }
}