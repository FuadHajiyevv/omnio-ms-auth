package az.atl.msauth.service.impl;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.ActivityReportService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class ActivityReportServiceImpl implements ActivityReportService {

    private final MessageFeignClient messageFeignClient;

    public ActivityReportServiceImpl(MessageFeignClient messageFeignClient) {
        this.messageFeignClient = messageFeignClient;
    }

    @Override
    public ActivityReportResponse getReport(String token, String lang) {
        localeResolver(lang);

        return messageFeignClient.getReport(token,lang).getBody();
    }

    private static void localeResolver(String lang) {
        if(Objects.isNull(lang) || lang.isEmpty()){
            lang = Locale.US.getLanguage();
        }
    }
}
