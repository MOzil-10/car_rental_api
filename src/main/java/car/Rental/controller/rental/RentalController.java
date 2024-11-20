package car.Rental.controller.rental;

import car.Rental.dto.RentalRequestDto;
import car.Rental.exception.CarServiceException;
import car.Rental.service.rental.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

}
