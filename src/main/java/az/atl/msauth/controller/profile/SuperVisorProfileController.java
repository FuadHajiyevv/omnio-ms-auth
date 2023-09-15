package az.atl.msauth.controller.profile;

import az.atl.msauth.dto.request.profile.RoleUpdateRequest;
import az.atl.msauth.dto.request.profile.SuperVisorProfileRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;
import az.atl.msauth.service.impl.SuperVisorProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Supervisor Profile")
@RequestMapping("/supervisor/profile")
public class SuperVisorProfileController {

    private final SuperVisorProfileServiceImpl service;

    public SuperVisorProfileController(SuperVisorProfileServiceImpl service) {
        this.service = service;
    }
    @Operation(summary = "Get all users list")

    @GetMapping
    public ResponseEntity<List<SuperVisorProfileRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get user by id")

    @GetMapping("/{id}")
    public ResponseEntity<SuperVisorProfileRequest> getById(
            @Min(value = 1, message = "validation.id.min") @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }
    @Operation(summary = "Ban user by id")

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteById(
            @Min(value = 1, message = "validation.id.min") @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @Operation(summary = "Change user's role id")

    @PostMapping("/{id}")
    public ResponseEntity<UpdateResponse> changeRole(
            @Min(1) @PathVariable(name = "id") Long id,
            @Valid @RequestBody RoleUpdateRequest role
    ) {
        return ResponseEntity.ok(service.changeRole(id, role));
    }

}
