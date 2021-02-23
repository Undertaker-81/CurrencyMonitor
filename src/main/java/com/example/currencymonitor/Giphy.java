package com.example.currencymonitor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Dmitriy Panfilov
 * 23.02.2021
 */
@FeignClient(name="giphy", url = "${url_api}")
public interface Giphy {

    @GetMapping("/search?api_key=${api_key}&q={find}&limit=1&offset=0")
    ResponseEntity<String> getGif(@PathVariable String find);

    @GetMapping("/random?api_key=${api_key}&tag={find}")
    ResponseEntity<String> getRandomGif(@PathVariable String find);
}
