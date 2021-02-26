package com.example.currencymonitor;


import com.example.currencymonitor.util.ExchangerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.math.BigDecimal;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Dmitriy Panfilov
 * 24.02.2021
 */

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 8080)
@SpringBootTest(webEnvironment = NONE)
public class OpenExchangerMockTest {


    @Autowired
    private OpenExchanger exchanger;

    @Value("${app_id}")
    private String appId;

    private final String body = "{\n" +
            "  \"AED\": \"United Arab Emirates Dirham\",\n" +
            "  \"AFN\": \"Afghan Afghani\",\n" +
            "  \"ALL\": \"Albanian Lek\",\n" +
            "  \"AMD\": \"Armenian Dram\",\n" +
            "  \"ANG\": \"Netherlands Antillean Guilder\",\n" +
            "  \"AOA\": \"Angolan Kwanza\",\n" +
            "  \"ARS\": \"Argentine Peso\",\n" +
            "  \"AUD\": \"Australian Dollar\"\n" +
            "}";

    @Test
    public void getCurrencyCode() throws JsonProcessingException {
        WireMock.stubFor(get(urlEqualTo("/api/currencies.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(body)
                        .withStatus(OK.value())));
      //  JsonNode jsonNode = objectMapper.readTree(response.getBody());
        Map<String, String>  response = exchanger.getCurrencyCode();
//        assertEquals(200, response.getStatusCode().value());
//        assertEquals(response.getBody(), body);
    }

    @Test
    public void getLatest() {
        WireMock.stubFor(get(urlEqualTo("/api/latest.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("{\n" +
                                "    \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                                "    \"license\": \"https://openexchangerates.org/license\",\n" +
                                "    \"timestamp\": 1614182400,\n" +
                                "    \"base\": \"USD\",\n" +
                                "    \"rates\": {\n" +
                                "        \"AED\": 3.673,\n" +
                                "        \"AFN\": 77.249998,\n" +
                                "        \"ALL\": 101.677655,\n" +
                                "        \"AMD\": 524.538424,\n" +
                                "        \"ANG\": 1.794606,\n" +
                                "        \"AOA\": 649.82,\n" +
                                "        \"ARS\": 89.6267,\n" +
                                "        \"AUD\": 1.262865,\n" +
                                "        \"AWG\": 1.8003\n" +
                                "    }\n" +
                                "}")
                        .withStatus(OK.value())));

        Map<String, BigDecimal> lastResult = exchanger.getLatest().getRates();
        assertEquals(9, lastResult.size());
        System.out.println(lastResult);
    }

    @Test
    public void comparatorTest(){
        WireMock.stubFor(get(urlEqualTo("/api/latest.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("latest.json")
                        .withStatus(OK.value())));
        Map<String, BigDecimal> lastResult = exchanger.getLatest().getRates();

        String date = "2021-02-23";
        WireMock.stubFor(get(urlEqualTo("/api/historical/" + date +".json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("history.json")
                        .withStatus(OK.value())));
        Map<String, BigDecimal> historyResult = exchanger.getHistory(date).getRates();
        //курс уменьшился
        assertEquals(-1, ExchangerUtil.comparator(lastResult, historyResult, "RUB", "AMD") );

    }

    @Test
    public void  comparatorTest2(){
        WireMock.stubFor(get(urlEqualTo("/api/latest.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("latest.json")
                        .withStatus(OK.value())));
        Map<String, BigDecimal> lastResult = exchanger.getLatest().getRates();

        String date = "2021-02-23";
        WireMock.stubFor(get(urlEqualTo("/api/historical/" + date +".json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("historyUP.json")
                        .withStatus(OK.value())));
        Map<String, BigDecimal> historyResult = exchanger.getHistory(date).getRates();
        //курс увеличился
        assertEquals(1, ExchangerUtil.comparator(lastResult, historyResult, "RUB", "AMD") );
    }
    @Test
    public void  errorTest() {
        WireMock.stubFor(get(urlEqualTo("/api/latest.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("{\n" +
                                "  \"error\": true,\n" +
                                "  \"status\": 401,\n" +
                                "  \"message\": \"invalid_app_id\",\n" +
                                "  \"description\": \"Invalid App ID provided - please sign up at https://openexchangerates.org/signup, or contact support@openexchangerates.org.\"\n" +
                                "}")
                        .withStatus(401)));

     }

    }