package com.brunnog.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

@RestController
@RequestMapping("/test")
public class CustomerController {

	@GetMapping
	public List findAll() {
		return ImmutableList.of(
	            1L, "Customer 1",
	            2L, "Customer 2",
	            3L, "Customer 3",
	            4L, "Customer 4");
	}
}
