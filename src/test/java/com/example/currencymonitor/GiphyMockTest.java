package com.example.currencymonitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class GiphyMockTest {

    @Autowired
    private Giphy giphy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api_key}")
    private String apiKey;

    @Test
    public void getRandomGif() throws JsonProcessingException {

        WireMock.stubFor(get(urlEqualTo("/v1/gifs/random?api_key="+ apiKey +"&tag=rich"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("giphy.json")
                        .withStatus(OK.value())));

        ResponseEntity<String> gifR = giphy.getRandomGif("rich");
        assertEquals(200, gifR.getStatusCode().value());
        JsonNode jsonNode = objectMapper.readTree(gifR.getBody());
        String urlGif = jsonNode.findValue("images").findValue("url").asText();
        assertEquals("gif", urlGif.substring(urlGif.length()-3) );
    }
}
