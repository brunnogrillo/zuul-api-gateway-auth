package com.brunnog.products.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ProductsController {

	@GetMapping
	public String findAll() {
		return "ok";
	}
}
