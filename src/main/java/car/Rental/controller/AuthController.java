package car.Rental.controller;

import car.Rental.dto.LoginDto;
import car.Rental.dto.UserDto;
import car.Rental.entity.User;
import car.Rental.service.auth.AuthService;
import car.Rental.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
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
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        User authenticatedUser = service.authenticateUser(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(jwtToken);
    }

}
