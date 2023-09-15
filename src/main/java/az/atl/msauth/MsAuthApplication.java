package az.atl.msauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"az.atl.msauth.feign"})
public class MsAuthApplication{
    public static void main(String[] args) {
        SpringApplication.run(MsAuthApplication.class, args);
    }
}
