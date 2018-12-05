package com.brunnog.gateway.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.brunnog.gateway.GatewayApplication;
import com.brunnog.gateway.dto.request.UserRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  GatewayApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

	private static final String AUTHORIZATION = "Authorization";
	private static final String LOGIN = "/login";
	private static final String USER = "/users";
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	public MockMvc mockMvc;	
	
	@Test
	public void succefullLogin() throws Exception {
		String user = getUser("brunno", "123");
		
		mockMvc.perform(post(LOGIN).content(user))
				.andExpect(status().isOk());
	}
	
	@Test
	public void unauthorizedLogin() throws Exception {
		String user = getUser("brunno", "1234");
		mockMvc.perform(post(LOGIN).content(user))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void forbiddenGetUser() throws Exception {
		mockMvc.perform(get(USER))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void succefullCreateUser() throws Exception {
		
		String user = getUser("brunno", "123");
		
		String authorization = mockMvc.perform(post(LOGIN).content(user))
				.andExpect(status().isOk())
				.andReturn().getResponse().getHeader(AUTHORIZATION);
		
		String newUser = getUser("brunno2", "123");
		
		mockMvc.perform(post(USER)
							.content(newUser)
							.contentType(APPLICATION_JSON_UTF8)
							.header(AUTHORIZATION, authorization))
		.andExpect(status().isOk());		
	}
		
	@Test
	public void failCreateUser() throws Exception {
		String user = getUser("brunno2", "123");		
		String newUser = getUser("brunno3", "123");
		
		String authorization = mockMvc.perform(post(LOGIN).content(user))
				.andExpect(status().isOk())
				.andReturn().getResponse().getHeader(AUTHORIZATION);
			
		mockMvc.perform(post(USER)
							.content(newUser)
							.contentType(APPLICATION_JSON_UTF8)
							.header(AUTHORIZATION, authorization))
		.andExpect(status().isForbidden());		
	}
	
	private static String getUser(String username, String password) throws JsonProcessingException {
		return mapper.writeValueAsString(new UserRequestDTO(username, password));
	}
}
