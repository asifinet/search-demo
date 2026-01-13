package com.example.demo.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalApiService {

  private final WebClient webClient;

  public ExternalApiService(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  public Map<String, Object> callHttpBinGet() {
    // returns JSON like: { "args":..., "headers":..., "origin":..., "url":... }
    return webClient.get()
        .uri("https://httpbin.org/get")
        .retrieve()
        .bodyToMono(Map.class)
        .block();
  }
}
