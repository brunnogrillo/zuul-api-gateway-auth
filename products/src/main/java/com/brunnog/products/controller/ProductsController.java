package com.brunnog.products.controller;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ProductsController {

	@GetMapping
	public Collection<?> findAll() {
		return Arrays.asList(
	            1L, "Product 1",
	            2L, "Product 2",
	            3L, "Product 3",
	            4L, "Product 4");
	}
}
