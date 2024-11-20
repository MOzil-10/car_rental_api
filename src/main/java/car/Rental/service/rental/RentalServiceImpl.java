package car.Rental.service.rental;

import car.Rental.dto.RentalRequestDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;
import car.Rental.entity.User;
import car.Rental.enums.Status;
import car.Rental.exception.CarServiceException;
import car.Rental.mapper.RentalMapper;
import car.Rental.repository.CarRepository;
import car.Rental.repository.RentalRequestRepository;
import car.Rental.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RentalServiceImpl implements RentalService {
    private final CarRepository carRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalRequestMapper;

    public RentalServiceImpl(CarRepository carRepository, RentalRequestRepository rentalRequestRepository,
                             UserRepository userRepository, RentalMapper rentalRequestMapper) {
        this.carRepository = carRepository;
        this.rentalRequestRepository = rentalRequestRepository;
        this.userRepository = userRepository;
        this.rentalRequestMapper = rentalRequestMapper;
    }

    @Override
    @Transactional
    public RentalRequestDto createRentalRequest(Long userId, Long carId, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new CarServiceException("Start date cannot be after end date", HttpStatus.BAD_REQUEST);
        }

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarServiceException("Car not found", HttpStatus.NOT_FOUND));

        if (!car.isAvailable()) {
            throw new CarServiceException("Car is not available for rental", HttpStatus.CONFLICT);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CarServiceException("User not found", HttpStatus.NOT_FOUND));

        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setUser(user);
        rentalRequest.setCar(car);
        rentalRequest.setStartDate(startDate);
        rentalRequest.setEndDate(endDate);
        rentalRequest.setStatus(Status.PENDING);

        rentalRequestRepository.save(rentalRequest);

        car.setAvailable(false);
        carRepository.save(car);

        return rentalRequestMapper.toRentalRequestDto(rentalRequest);
    }
}
