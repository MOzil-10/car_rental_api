package car.Rental.service.user;

import car.Rental.dto.UserDto;

public interface UserService {
    UserDto getUserProfile(Long userId);
    UserDto updateProfile(Long userId, UserDto userDto);
}
