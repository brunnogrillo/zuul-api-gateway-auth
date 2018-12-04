package com.brunnog.gateway.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = "production")
@RestController
public class DisableSwaggerUiController {

	@GetMapping("swagger-ui.html")
	public ResponseEntity<Void> getSwagger(HttpServletResponse httpResponse) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
