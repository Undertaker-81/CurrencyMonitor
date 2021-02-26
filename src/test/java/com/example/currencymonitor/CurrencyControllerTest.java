package com.example.currencymonitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Panfilov Dmitriy
 * 26.02.2021
 */
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = CurrencyController.class)//так не работает, не цепляет бин feign
    @SpringBootTest
    @ExtendWith(SpringExtension.class)
    @AutoConfigureWireMock(port = 8081)
    @AutoConfigureMockMvc  //а так работает
class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
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
    void getCurrencyCode() throws Exception {

        WireMock.stubFor(get(urlEqualTo("/api/currencies.json?app_id=" + appId))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(body)
                        .withStatus(OK.value())));
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType("application/json"))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    @Test
    void getLatest() {
    }
}