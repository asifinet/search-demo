package com.example.demo.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.Loggable;
import com.example.demo.service.ExternalApiService;

@Loggable
@RestController
@RequestMapping("/api/external")
public class ExternalController {

  private final ExternalApiService externalApiService;

  public ExternalController(ExternalApiService externalApiService) {
    this.externalApiService = externalApiService;
  }

  @GetMapping("/httpbin")
  public Map<String, Object> callHttpBin() {
    return externalApiService.callHttpBinGet();
  }
}
