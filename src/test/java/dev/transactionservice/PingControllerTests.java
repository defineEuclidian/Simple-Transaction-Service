package dev.transactionservice;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * PingControllerTests class to test the ping endpoint.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void pingTest() throws Exception { // Check if /ping results in a 200 Ok HTTP response code

		this.mockMvc.perform(get("/ping"))
					.andExpect(status().isOk());
	}

}
