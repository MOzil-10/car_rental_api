package car.Rental.controller.authController;

import car.Rental.dto.LoginDto;
import car.Rental.dto.UserDto;
import car.Rental.entity.User;
import car.Rental.response.LoginResponse;
import car.Rental.service.auth.AuthService;
import car.Rental.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final AuthService service;
    private final JwtService jwtService;

    public AuthController(AuthService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User registeredUser = service.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
        User authenticatedUser = service.authenticateUser(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Date issuedAt = jwtService.extractIssuedAt(jwtToken);
        long expiresInMinutes = jwtService.getExpirationTime() / 1000 / 60;
        Map<String, Object> claims = jwtService.extractAllClaims(jwtToken);

        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresInMinutes(expiresInMinutes)
                .setIssuedAt(issuedAt.getTime())
                .setClaims(claims);

        return ResponseEntity.ok(loginResponse);
    }
}
