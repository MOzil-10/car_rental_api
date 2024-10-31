package car.Rental.service.auth;

import car.Rental.dto.LoginDto;
import car.Rental.dto.UserDto;
import car.Rental.entity.User;

public interface AuthService {

    User registerUser(UserDto userInput);
    User authenticateUser(LoginDto userInput);
}
