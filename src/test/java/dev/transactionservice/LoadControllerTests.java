package dev.transactionservice;

import dev.transactionservice.models.EventProcessor;
import dev.transactionservice.models.LoadRequestJson;
import dev.transactionservice.models.LoadResponseJson;

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
 * LoadControllerTests class to test the load endpoint, using the MockMvc entry point.
 * 
 * <p> Every test name follows this scheme: </p>
 * <p> - "load" is the first part of the name </p>
 * <p> - Following "load" is either "One" or "Multiple", denoting the amount of load requests </p>
 * <p> - Following "One" or "Multiple" is a combination of attributes designated by "With"; these attributes are connected by "And" </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoadControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void resetEventProcessor(@Autowired EventProcessor eventProcessor) {
		eventProcessor.clear();
	}

	@Test
	public void loadOneWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", ".1", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "0.10", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1)) // Check the final load response
					.andExpect(status().isCreated()); // Check to see if /load resulted in a 201 Created HTTP response code

	}

	@Test
	public void loadOneWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", ".12", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "0.12", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void loadOneWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", ".12745", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "0.12", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void loadOneInt() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "100.00", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1))
					.andExpect(status().isCreated());

	}

	@Test
	public void loadOneIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.1", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "100.10", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void loadOneIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.12", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "100.12", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void loadOneIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.12745", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "100.12", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1));

	}

	@Test
	public void loadMultipleInt() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.00", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2)) // Chain the last request with the next request
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.1", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.2", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.3", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.60", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.12", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.13", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.14", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.39", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.12923", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.134423", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.1425", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.39", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithOneDecimal() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.2", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.1", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.30", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.21", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.33", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.21132", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12321", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.33", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithOneDecimalAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.2", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.3", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.62", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithOneDecimalAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.2", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.3", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12321", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.62", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.22", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.33", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12321", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.67", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithOneDecimalAndIntWithTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.3", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.12", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.42", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithOneDecimalAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.3", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.1234", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.42", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntAndIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.32", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.1234", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.44", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleIntWithOneDecimalAndIntWithTwoDecimalsAndIntWithMoreThanTwoDecimals() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100.1", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100.32", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100.1234", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("2", "0", "APPROVED", "300.54", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response1))));

	}

	@Test
	public void loadMultipleWithDifferentUserId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100", "USD", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "1", "100", "USD", "CREDIT").toString();

		String response1 = new LoadResponseJson("1", "0", "APPROVED", "200.00", "USD", "CREDIT").toString();
		String response2 = new LoadResponseJson("2", "1", "APPROVED", "100.00", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response1)) // Check the final load response of user 0
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response2)))); // Check the final load response of user 1

	}

	@Test
	public void loadMultipleWithDifferentCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("1", "0", "100", "EUR", "CREDIT").toString();
		String request3 = new LoadRequestJson("2", "0", "100", "GBP", "CREDIT").toString();

		String response1 = new LoadResponseJson("0", "0", "APPROVED", "100.00", "USD", "CREDIT").toString();
		String response2 = new LoadResponseJson("1", "0", "APPROVED", "100.00", "EUR", "CREDIT").toString();
		String response3 = new LoadResponseJson("2", "0", "APPROVED", "100.00", "GBP", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(content().json(response1)) // Check the first load response
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(content().json(response2)) // Check the second load response
					.andDo(result2 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request3))
					.andExpect(content().json(response3)))); // Check the third load response

	}

	@Test
	public void loadOneWithEmptyUserId() throws Exception {

		String request1 = new LoadRequestJson("0", "", "100", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity()); // Check if this error scenario results in a 422 Unprocessable Entity HTTP response code

	}

	@Test
	public void loadOneWithEmptyMsgId() throws Exception {

		String request1 = new LoadRequestJson("", "0", "100", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void loadMultipleWithSameMsgId() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();
		String request2 = new LoadRequestJson("0", "0", "100", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andDo(result1 -> mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request2))
					.andExpect(status().isUnprocessableEntity()));

	}
	
	@Test
	public void loadOneWithEmptyAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithNegativeAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "-100", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithZeroAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "0", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithNonNumericAmount() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100test", "USD", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithEmptyCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithNonAlphabeticalCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "US1", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithInvalidLengthCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USDA", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void loadOneWithInvalidCaseCurrency() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "usd", "CREDIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}
	
	@Test
	public void loadOneWithInvalidDebitCredit() throws Exception {

		String request1 = new LoadRequestJson("0", "0", "100", "USD", "DEBIT").toString();

		this.mockMvc.perform(put("/load").contentType(MediaType.APPLICATION_JSON).content(request1))
					.andExpect(status().isUnprocessableEntity());

	}

}
