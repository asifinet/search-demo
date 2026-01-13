package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class SimpleTestController {
	 @GetMapping("/hello")
	    public String hello() {
	        return "Hello World";
	    }
	    
	    @GetMapping("/customers")
	    public String customers() {
	        return "Customers list";
	    }
}
