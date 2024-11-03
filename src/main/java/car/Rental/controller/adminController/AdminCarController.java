package car.Rental.controller.adminController;

import car.Rental.dto.RentalRequestDto;
import car.Rental.dto.carDto;
import car.Rental.entity.Car;
import car.Rental.service.admin.cars.adminCarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminCarController {

    private final adminCarService adminCarService;

    public AdminCarController(adminCarService adminCarService) {
        this.adminCarService = adminCarService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody carDto carDto) throws IOException {
        Car newCar = adminCarService.addCar(carDto);

        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<carDto>> getAllCars() {
        List<carDto> cars = adminCarService.getAllCars();

        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        boolean deleted = adminCarService.deleteCar(id);

        if (deleted) {
            return ResponseEntity.ok("Car deleted Successfully.");
        } else {
            return ResponseEntity.status(404).body("Car not found");
        }
    }

    @GetMapping("/rentals/pending")
    public ResponseEntity<List<RentalRequestDto>> getPendingRentals() {
        List<RentalRequestDto> pendingRentals = adminCarService.getPendingRentals();
        return ResponseEntity.ok(pendingRentals);
    }

    @PostMapping("/rentals/{requestId}/approve")
    public ResponseEntity<RentalRequestDto> approveRentalRequest(@PathVariable Long requestId) {
        RentalRequestDto approvedRequest = adminCarService.approveRentalRequest(requestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PostMapping("/rentals/{requestId}/reject")
    public ResponseEntity<RentalRequestDto> rejectRentalRequest(
            @PathVariable Long requestId,
            @RequestParam String rejectionReason) {
        RentalRequestDto rejectedRequest = adminCarService.rejectRentalRequest(requestId, rejectionReason);
        return ResponseEntity.ok(rejectedRequest);
    }
}
