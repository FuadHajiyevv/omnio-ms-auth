package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.service.impl.ActivityReportServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityReportController {

    private final ActivityReportServiceImpl activityReportService;

    public ActivityReportController(ActivityReportServiceImpl activityReportService) {
        this.activityReportService = activityReportService;
    }

    @GetMapping("/report")
    public ResponseEntity<ActivityReportResponse> getReport(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language",required = false)String lang
    ){
        return ResponseEntity.ok(activityReportService.getReport(token,lang));
    }
}
