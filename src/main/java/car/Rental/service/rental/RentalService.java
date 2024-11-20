package car.Rental.service.rental;

import car.Rental.dto.RentalRequestDto;

import java.time.LocalDateTime;

public interface RentalService {

    RentalRequestDto createRentalRequest(Long userId, Long carId, LocalDateTime startDate, LocalDateTime endDate);

}
