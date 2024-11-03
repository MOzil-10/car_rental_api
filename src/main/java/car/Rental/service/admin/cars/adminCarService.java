package car.Rental.service.admin.cars;

import car.Rental.dto.RentalRequestDto;
import car.Rental.dto.carDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;

import java.io.IOException;
import java.util.List;

public interface adminCarService {

    Car addCar(carDto carDto) throws IOException;
    List<carDto> getAllCars();
    List<carDto> getCarByName(String name);
    List<Car> findByModel(String model);
    List<Car> findByIsAvailable(boolean isAvailable);
    List<Car> findByMakeAndModel(String make, String model);
    List<Car> findByRentalRateLessThanEqual(double rentalRate);
    List<RentalRequestDto> getPendingRentals();
    boolean deleteCar(Long id);
    RentalRequestDto approveRentalRequest(Long requestId);
    RentalRequestDto rejectRentalRequest(Long requestId, String rejectionReason);
    RentalRequestDto convertToDto(RentalRequest rentalRequest);
}
