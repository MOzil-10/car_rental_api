package car.Rental.repository;

import car.Rental.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRequestRepository extends JpaRepository<RentalRequest,Long> {
}
