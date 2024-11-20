package car.Rental.service.admin;

import car.Rental.dto.RentalRequestDto;
import car.Rental.dto.CarDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;
import car.Rental.enums.Status;
import car.Rental.mapper.MapperUtil;
import car.Rental.mapper.RentalMapper;
import car.Rental.repository.CarRepository;
import car.Rental.repository.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AdminCarServiceImpl implements AdminCarService {

    private final CarRepository carRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final RentalMapper rentalMapper;

    @Autowired
    public AdminCarServiceImpl(CarRepository carRepository, RentalRequestRepository rentalRequestRepository, RentalMapper rentalMapper) {
        this.carRepository = carRepository;
        this.rentalRequestRepository = rentalRequestRepository;
        this.rentalMapper = rentalMapper;
    }

    @Override
    public Car addCar(CarDto carDto) throws IOException {
        Car car = MapperUtil.toCarEntity(carDto);
        return carRepository.save(car);
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(MapperUtil::toCarDto)
                .toList();
    }

    @Override
    public List<Car> findByRentalRateLessThanEqual(double rentalRate) {
        return carRepository.findByRentalRateLessThanEqual(rentalRate);
    }

    @Override
    public boolean deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<RentalRequestDto> getPendingRentals() {
        return rentalRequestRepository.findAll()
                .stream()
                .filter(request -> request.getStatus() == Status.PENDING)
                .map(rentalMapper::toRentalRequestDto)
                .toList();
    }

    @Override
    public RentalRequestDto approveRentalRequest(Long requestId) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        rentalRequest.setStatus(Status.APPROVED);
        rentalRequestRepository.save(rentalRequest);

        return rentalMapper.toRentalRequestDto(rentalRequest);
    }

    @Override
    public RentalRequestDto rejectRentalRequest(Long requestId, String rejectionReason) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        rentalRequest.setStatus(Status.REJECTED);
        rentalRequest.setRejectionReason(rejectionReason);
        rentalRequestRepository.save(rentalRequest);

        return rentalMapper.toRentalRequestDto(rentalRequest);
    }
}
