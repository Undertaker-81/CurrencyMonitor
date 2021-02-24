package com.example.currencymonitor;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
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

    private final String find = "rich";

    @Test
    public void getRandomGif(){

        WireMock.stubFor(get(urlEqualTo("/random?api_key=zZ7jf9JcooWaOIpwTk4DbyLZMoOMavDx&tag=" + find))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("body")
                        .withStatus(OK.value())));
    }
}
