package com.example.currencymonitor;

import com.example.currencymonitor.model.ExchangerData;
import com.example.currencymonitor.util.ExchangerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.MathContext;

@SpringBootTest
class CurrencyMonitorApplicationTests {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OpenExchanger exchanger;

    @Autowired
    private Giphy giphy;

    private final String curr = "BYN";

    @Value("${currency}")
     private String currency;

    @Test
    void contextLoads() {
        ExchangerData dataLast = exchanger.getLatest();
        ExchangerData dataHis = exchanger.getHistory(ExchangerUtil.getYesterday());
        BigDecimal rub = exchanger.getLatest().getRates().get(currency);
        BigDecimal cny = exchanger.getLatest().getRates().get(curr);
        System.out.println("RUB = " + rub);
        System.out.println("CNY = " + cny);
        System.out.println(rub.divide(cny, new MathContext(20)).toString());
        System.out.println("--------------------");
        BigDecimal rub1 = exchanger.getHistory(ExchangerUtil.getYesterday()).getRates().get(currency);
        BigDecimal cny1 = exchanger.getHistory(ExchangerUtil.getYesterday()).getRates().get(curr);
        System.out.println("RUB = " + rub1);
        System.out.println("CNY = " + cny1);
        System.out.println(rub1.divide(cny1, new MathContext(20)).toString());
        System.out.println(rub.subtract(cny));
        System.out.println(rub1.subtract(cny1));
        System.out.println("-1 - уменьшилось");
        System.out.println(ExchangerUtil.comparator(dataLast.getRates(), dataHis.getRates(), currency, curr));
       // System.out.println(data.getRates().get(currency));
    }
    @Test
    void testGif() throws JsonProcessingException {
       // ResponseEntity<String> gif = giphy.getGif("rich");
       // System.out.println(gif.getBody());
        ResponseEntity<String> gifR = giphy.getRandomGif("rich");
        JsonNode jsonNode = objectMapper.readTree(gifR.getBody());
        String url = jsonNode.findValue("images").findValue("url").asText();
        System.out.println(url);
        System.out.println(gifR.getBody().replaceAll("\\\\", ""));
    }

}
