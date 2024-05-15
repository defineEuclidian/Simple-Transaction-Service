package dev.transactionservice;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CustomErrorControllerTests class to test the error endpoint, using the MockMvc entry point.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void errorTest() throws Exception { // Check if /error results in a 200 Ok HTTP response code

		this.mockMvc.perform(get("/error"))
					.andExpect(status().isOk());
	}

}
