package car.Rental.service.car;

import car.Rental.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> browseAvailableCars();
    List<CarDto> browseCarByModel(String model);
    List<CarDto> browseCarByMake(String make);
}
