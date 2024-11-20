package car.Rental.service.user;

import car.Rental.dto.UserDto;
import car.Rental.entity.User;
import car.Rental.exception.CarServiceException;
import car.Rental.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto getUserProfile(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(()-> new CarServiceException("User not found", HttpStatus.NOT_FOUND));

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                 user.getPhoneNumber(), user.isHasPendingRental(), user.getRole(), user.getPassword());
    }

    public UserDto updateProfile(Long userId, UserDto userDto) {
        User user = repository.findById(userId)
                .orElseThrow(()-> new CarServiceException("User not found", HttpStatus.NOT_FOUND));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());

        repository.save(user);

        return userDto;
    }
}
