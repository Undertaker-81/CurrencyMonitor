package com.example.currencymonitor;

import com.example.currencymonitor.util.ExchangerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author Dmitriy Panfilov
 * 22.02.2021
 */
@RestController
@RequestMapping(value = "/")
public class CurrencyController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OpenExchanger exchanger;

    @Autowired
    private Giphy giphy;

    @Value("${currency}")
    private String baseCurrency;

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getCurrencyCode(){
        return exchanger.getCurrencyCode();
    }

    @GetMapping(value = "/currency/{currency}")
    String getLatest(@PathVariable String currency) {
        try {
            Map<String, BigDecimal> lastResult = exchanger.getLatest().getRates();
            Map<String, BigDecimal> yesterdayResult = exchanger.getHistory(ExchangerUtil.getYesterday()).getRates();
            int comp = ExchangerUtil.comparator(lastResult, yesterdayResult, baseCurrency, currency.toUpperCase());
            String findGif = "";
            if (comp==1){
                findGif="rich" ;
            }
            else findGif = "broke";
            ResponseEntity<String> gifR = giphy.getRandomGif(findGif);
            JsonNode jsonNode = objectMapper.readTree(gifR.getBody());
            return "<img src=" + jsonNode.findValue("images").findValue("url").asText() + " alt=gif>";
        }catch (Exception e){
            return e.getMessage();
        }

    }


}
