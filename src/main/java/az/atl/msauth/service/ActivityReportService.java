package az.atl.msauth.service;

import az.atl.msauth.dto.response.message.ActivityReportResponse;

public interface ActivityReportService {

    ActivityReportResponse getReport(String token, String lang);
}
