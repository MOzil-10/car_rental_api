package car.Rental.service.auth;

import car.Rental.dto.LoginDto;
import car.Rental.dto.UserDto;
import car.Rental.entity.User;
import car.Rental.enums.Role;
import car.Rental.exception.UserServiceException;
import car.Rental.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User registerUser(UserDto userInput) {
        Optional<User> existingUser = userRepository.findByEmail(userInput.getEmail());
        if (existingUser.isPresent()) {
            throw new UserServiceException("User with email " + userInput.getEmail() + " already exist", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setFirstName(userInput.getFirstName());
        user.setLastName(userInput.getLastName());
        user.setEmail(userInput.getEmail());
        user.setRole(userInput.getRole() != null ? userInput.getRole() : Role.CUSTOMER);
        user.setPhoneNumber(userInput.getPhoneNumber());
        user.setHasPendingRental(userInput.isHasPendingRental());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(LoginDto userInput) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userInput.getEmail(),
                            userInput.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new UserServiceException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        return userRepository.findByEmail(userInput.getEmail())
                .orElseThrow(() -> new UserServiceException("User not found", HttpStatus.NOT_FOUND));
    }

    @PostConstruct
    public void createAdmin() {
        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
            User user = new User();
            user.setEmail("mukosi@test.com");
            user.setFirstName("Mukosi");
            user.setLastName("Ozil");
            user.setRole(Role.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }
}
