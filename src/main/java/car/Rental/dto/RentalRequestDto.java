package car.Rental.dto;

import car.Rental.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalRequestDto {
    private Long id;
    private Long userId;
    private Long carId;
    private String carMake;
    private String carModel;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;
    private String rejectionReason;
}
