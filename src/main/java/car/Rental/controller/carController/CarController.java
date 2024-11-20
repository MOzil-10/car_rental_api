package car.Rental.controller.carController;

import car.Rental.dto.CarDto;
import car.Rental.service.car.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDto>> getCars(@RequestParam(defaultValue = "true") boolean isAvailable) {
        List<CarDto> cars = service.browseAvailableCars();

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/model")
    public ResponseEntity<List<CarDto>> getCarByModel(@RequestParam String model) {
        List<CarDto> cars = service.browseCarByModel(model);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/make")
    public ResponseEntity<List<CarDto>> getCarByMake(@RequestParam String make) {
        List<CarDto> cars = service.browseCarByMake(make);
        return ResponseEntity.ok(cars);
    }
}
