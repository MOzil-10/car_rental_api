package car.Rental.service.admin;

import car.Rental.dto.RentalRequestDto;
import car.Rental.dto.CarDto;
import car.Rental.entity.Car;

import java.io.IOException;
import java.util.List;

public interface AdminCarService {

    Car addCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    List<Car> findByRentalRateLessThanEqual(double rentalRate);

    List<RentalRequestDto> getPendingRentals();

    boolean deleteCar(Long id);

    RentalRequestDto approveRentalRequest(Long requestId);

    RentalRequestDto rejectRentalRequest(Long requestId, String rejectionReason);
}
