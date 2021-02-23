package com.example.currencymonitor;

import com.example.currencymonitor.model.ExchangerData;
import com.example.currencymonitor.util.ExchangerUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Dmitriy Panfilov
 * 22.02.2021
 */
@RestController
@RequestMapping(value = "/",produces =  MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CurrencyController {

    OpenExchanger exchanger;

    @GetMapping()
    ResponseEntity<String> getHello(){
        return exchanger.getCurrencyCode();
    }

    @GetMapping(value = "latest")
    ExchangerData getLatest(){
        return exchanger.getLatest();
    }

    @GetMapping(value = "history")
    ExchangerData getHistory(){
        return exchanger.getHistory(ExchangerUtil.getYesterday());
    }
}
