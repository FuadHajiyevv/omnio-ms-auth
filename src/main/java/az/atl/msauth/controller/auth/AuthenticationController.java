package az.atl.msauth.controller.auth;


import az.atl.msauth.dto.request.auth.AuthRequest;
import az.atl.msauth.dto.request.auth.RefreshRequest;
import az.atl.msauth.dto.request.auth.RegisterRequest;
import az.atl.msauth.dto.response.auth.AuthResponse;
import az.atl.msauth.dto.response.auth.RefreshResponse;
import az.atl.msauth.dto.response.auth.RegisterResponse;
import az.atl.msauth.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {


    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @Operation(summary = "Registration")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(summary = "Authentication")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@Valid @RequestBody RefreshRequest refreshToken) {
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }
}
