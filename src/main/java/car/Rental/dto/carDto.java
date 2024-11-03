package car.Rental.dto;

import lombok.Data;

@Data
public class carDto {

    private Long id;
    private String make;
    private String model;
    private String registrationNumber;
    private boolean isAvailable;
    private double rentalRate;
}
