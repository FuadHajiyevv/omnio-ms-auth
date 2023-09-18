package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.service.impl.ActivityReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Activity report")
@RequestMapping("/agent/activity")
public class ActivityReportController {

    private final ActivityReportServiceImpl activityReportService;

    public ActivityReportController(ActivityReportServiceImpl activityReportService) {
        this.activityReportService = activityReportService;
    }

    @Operation(summary = "User's activity report")
    @GetMapping("/report")
    public ResponseEntity<ActivityReportResponse> getReport(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(activityReportService.getReport(token, lang));
    }
}
