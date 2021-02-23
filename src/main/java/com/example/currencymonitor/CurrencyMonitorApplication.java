package com.example.currencymonitor;

import com.example.currencymonitor.model.ExchangerData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.*;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
public class CurrencyMonitorApplication {


    public static void main(String[] args) {
        SpringApplication.run(CurrencyMonitorApplication.class, args);

    }

}
