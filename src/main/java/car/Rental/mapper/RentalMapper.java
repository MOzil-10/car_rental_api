package car.Rental.mapper;

import car.Rental.dto.CarDto;
import car.Rental.dto.RentalRequestDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    RentalRequestDto toRentalRequestDto(RentalRequest rentalRequest);

    CarDto toCarDto(Car car);

    Car toCarEntity(CarDto carDto);
}