package car.Rental.repository;

import car.Rental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByIsAvailableTrue();
    List<Car> findByModel(String model);
    List<Car> findByMake(String make);
    List<Car> findByRentalRateLessThanEqual(double rentalRate);
}
