package car.Rental.exception;

import org.springframework.http.HttpStatus;

public class CarServiceException extends RuntimeException{

    private final HttpStatus status;

    public CarServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
