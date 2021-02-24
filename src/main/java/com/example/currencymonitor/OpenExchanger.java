package com.example.currencymonitor;

import com.example.currencymonitor.model.ExchangerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author Dmitriy Panfilov
 * 22.02.2021
 */
@FeignClient(name="exchanger", url = "${exchangerUrl}")
public interface OpenExchanger {


    @GetMapping("currencies.json?app_id=${app_id}")
    ResponseEntity<String> getCurrencyCode();

    @GetMapping("latest.json?app_id=${app_id}")
    ExchangerData getLatest();

    @GetMapping("historical/{date}.json?app_id=${app_id}")
    ExchangerData getHistory(@PathVariable String date);


}
