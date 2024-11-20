package car.Rental.controller.userController;

import car.Rental.dto.UserDto;
import car.Rental.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{userId}/profile")
    public UserDto getUserProfile(@PathVariable Long userId) {
        return service.getUserProfile(userId);
    }

    @PutMapping("/{userId}/profile")
    public UserDto updateUserProfile(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return service.updateProfile(userId, userDto);
    }
}
