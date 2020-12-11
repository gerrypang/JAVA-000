package com.gerry.pang.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author Gerry_Pang
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

	@Test
	public void whenQueryListSucess(@Autowired MockMvc mockMvc) throws Exception {
		mockMvc.perform(get("/store").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(3));
	}
	
	
	@Test
	public void whenQuerySucess(@Autowired MockMvc mockMvc) throws Exception {
		mockMvc.perform(get("/store").contentType(MediaType.APPLICATION_JSON).param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("get store 1"));
	}
	
}
