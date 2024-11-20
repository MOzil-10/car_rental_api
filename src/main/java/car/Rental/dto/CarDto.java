package car.Rental.dto;

import lombok.Data;

@Data
public class CarDto {

    private Long id;
    private String make;
    private String model;
    private boolean isAvailable;
    private double rentalRate;
}
