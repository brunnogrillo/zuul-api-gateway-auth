package com.brunnog.products.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

@RestController
@RequestMapping("/test")
public class ProductsController {

	@GetMapping
	public List findAll() {
		return ImmutableList.of(
	            1L, "Product 1",
	            2L, "Product 2",
	            3L, "Product 3",
	            4L, "Product 4");
	}
}
