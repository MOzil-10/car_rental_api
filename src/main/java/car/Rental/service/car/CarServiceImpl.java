package car.Rental.service.car;

import car.Rental.dto.CarDto;
import car.Rental.entity.Car;
import car.Rental.mapper.MapperUtil;
import car.Rental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarDto> browseAvailableCars() {
        List<Car> availableCars = repository.findByIsAvailableTrue();
        return MapperUtil.toCarDtoList(availableCars);
    }

    @Override
    public List<CarDto> browseCarByModel(String model) {
        List<Car> cars = repository.findByModel(model);
        return MapperUtil.toCarDtoList(cars);
    }

    @Override
    public List<CarDto> browseCarByMake(String make) {
        List<Car> cars = repository.findByMake(make);
        return MapperUtil.toCarDtoList(cars);
    }
}