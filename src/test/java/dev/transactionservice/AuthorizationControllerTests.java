package dev.transactionservice;

import dev.transactionservice.models.AuthorizationRequestJson;
import dev.transactionservice.models.AuthorizationResponseJson;
import dev.transactionservice.models.EventProcessor;
import dev.transactionservice.models.LoadRequestJson;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

/**
 * AuthorizationControllerTests class to test the authorization endpoint, using the MockMvc entry point.
 * 
 * <p> Every test name follows this scheme: </p>
 * <p> - "authorize" is the first part of the name </p>
 * <p> - Following "authorize" is either "One" or "Multiple", denoting the amount of authorization requests </p>
 * <p> - Following "One" or "Multiple" is a combination of attributes designated by "With"; these attributes are connected by "And" </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void resetEventProcessor(@Autowired EventProcessor eventProcessor) {
		eventProcessor.clear();
	}

	@Test
	public void authorizeOneWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", ".1", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "249.90", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)) // Check the final authorization response
					.andExpect(status().isCreated())); // Check to see if /authorization resulted in a 201 Created HTTP response code

	}

	@Test
	public void authorizeOneWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", ".12", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "249.88", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", ".12745", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "249.88", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneInt() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "150.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2)) // Chain the last request with the next request
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.1", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "149.90", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.12", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "149.88", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.12745", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "149.88", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeMultipleInt() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "200.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.1", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.2", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.3", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.40", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.12", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.13", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.14", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.61", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.12923", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.134423", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.1425", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.61", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.2", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.1", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.70", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.21", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.67", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.21132", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12321", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.67", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithOneDecimalAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.2", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.3", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.38", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithOneDecimalAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.2", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.3", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12321", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.38", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.22", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.33", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12321", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.33", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithOneDecimalAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.3", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.12", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.58", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithOneDecimalAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.3", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.1234", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.58", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntAndIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.32", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.1234", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.56", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleIntWithOneDecimalAndIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "500", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100.1", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100.32", "USD", "DEBIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100.1234", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "199.46", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)))));

	}

	@Test
	public void authorizeMultipleWithDifferentUserId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "300", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new LoadRequestJson("2", "1", "200", "USD", "CREDIT").toString();
		String request4 = new AuthorizationRequestJson("3", "1", "100", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "APPROVED", "200.00", "USD", "DEBIT").toString();
		String response2 = new AuthorizationResponseJson("3", "1", "APPROVED", "100.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)) // Check the final authorization response of user 0
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response2))))); // Check the final authorization response of user 1

	}

	@Test
	public void authorizeMultipleWithDifferentCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "400", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "300", "EUR", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "200", "GBP", "CREDIT").toString();
		String request4 = new AuthorizationRequestJson("3", "0", "100", "USD", "DEBIT").toString();
		String request5 = new AuthorizationRequestJson("4", "0", "100", "EUR", "DEBIT").toString();
		String request6 = new AuthorizationRequestJson("5", "0", "100", "GBP", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("3", "0", "APPROVED", "300.00", "USD", "DEBIT").toString();
		String response2 = new AuthorizationResponseJson("4", "0", "APPROVED", "200.00", "EUR", "DEBIT").toString();
		String response3 = new AuthorizationResponseJson("5", "0", "APPROVED", "100.00", "GBP", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andDo(result3 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request4))
					.andExpect(content().json(response1)) // Check the first authorization response
					.andDo(result4 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request5))
					.andExpect(content().json(response2)) // Check the second authorization response
					.andDo(result5 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request6))
					.andExpect(content().json(response3))))))); // Check the third authorization response

	}

	@Test
	public void authorizeOneWithNonExistantBalance() throws Exception {

		String request1 = new AuthorizationRequestJson("0", "0", "100", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("0", "0", "DECLINED", "0.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void authorizeOneWithEmptyBalance() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USD", "DEBIT").toString();
		String request3 = new AuthorizationRequestJson("2", "0", "100", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("2", "0", "DECLINED", "0.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void authorizeOneWithInsufficientBalance() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "200", "USD", "DEBIT").toString();

		String response1 = new AuthorizationResponseJson("1", "0", "DECLINED", "100.00", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)));

	}

	@Test
	public void authorizeOneWithEmptyUserId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "", "100", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity())); // Check if this error scenario results in a 422 Unprocessable Entity HTTP response code

	}

	@Test
	public void authorizeOneWithEmptyMsgId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("", "0", "100", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}

	@Test
	public void authorizeOneWithSameMsgId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("0", "0", "100", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithEmptyAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithNegativeAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "-100", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithZeroAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "0", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithNonNumericAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100test", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithEmptyCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithNonAlphabeticalCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "US1", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithInvalidLengthCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "USDA", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}

	@Test
	public void authorizeOneWithInvalidCaseCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "usd", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("1", "0", "100", "usd", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void authorizeOneWithInvalidDebitCredit() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "250", "USD", "CREDIT").toString();
		String request2 = new AuthorizationRequestJson("0", "0", "100", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/authorization").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}

}
