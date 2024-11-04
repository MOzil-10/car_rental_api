package car.Rental.service.admin.cars;

import car.Rental.dto.RentalRequestDto;
import car.Rental.dto.carDto;
import car.Rental.entity.Car;
import car.Rental.entity.RentalRequest;
import car.Rental.enums.Status;
import car.Rental.repository.CarRepository;
import car.Rental.repository.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class adminCarServiceImpl implements adminCarService{

    private final CarRepository carRepository;
    private final RentalRequestRepository rentalRequestRepository;

    @Autowired
    public adminCarServiceImpl(CarRepository carRepository, RentalRequestRepository rentalRequestRepository){
        this.carRepository = carRepository;
        this.rentalRequestRepository = rentalRequestRepository;
    }

    @Override
    public Car addCar(carDto carDto) throws IOException {
        Car car = convertToEntity(carDto);
        return carRepository.save(car);
    }

    @Override
    public List<carDto> getAllCars() {
        return carRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<carDto> getCarByName(String name) {
        return carRepository.findByModel(name).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }

    @Override
    public List<Car> findByIsAvailable(boolean isAvailable) {
        return carRepository.findByIsAvailable(isAvailable);
    }

    @Override
    public List<Car> findByMakeAndModel(String make, String model) {
        return carRepository.findByMakeAndModel(make, model);
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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalRequestDto approveRentalRequest(Long requestId) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        rentalRequest.setStatus(Status.APPROVED);
        rentalRequestRepository.save(rentalRequest);

        return convertToDto(rentalRequest);
    }

    @Override
    public RentalRequestDto rejectRentalRequest(Long requestId, String rejectionReason) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        rentalRequest.setStatus(Status.REJECTED);
        rentalRequest.setRejectionReason(rejectionReason);
        rentalRequestRepository.save(rentalRequest);

        return convertToDto(rentalRequest);
    }

    public RentalRequestDto convertToDto(RentalRequest rentalRequest) {
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

    private Car convertToEntity(carDto carDto) {
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setRegistrationNumber(carDto.getRegistrationNumber());
        car.setAvailable(carDto.isAvailable());
        car.setRentalRate(carDto.getRentalRate());
        return car;
    }

    private carDto convertToDto(Car car) {
        carDto dto = new carDto();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setRegistrationNumber(car.getRegistrationNumber());
        dto.setAvailable(car.isAvailable());
        dto.setRentalRate(car.getRentalRate());
        return dto;
    }

    public List<RentalRequestDto> convertToDtoList(List<RentalRequest> rentalRequests) {
        return rentalRequests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
