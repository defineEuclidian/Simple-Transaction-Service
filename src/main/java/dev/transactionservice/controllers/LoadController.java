package dev.transactionservice.controllers;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.transactionservice.models.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

import java.math.BigDecimal;

/**
 * LoadController class to process load requests and responses through the /load path.
 * 
 * <p> Consequently, it adds funds to a user's account. </p>
 */
@RestController
@ResponseStatus(HttpStatus.CREATED)
public class LoadController {

	@Autowired
	private EventProcessor eventProcessor; // Autowired event processor to allow for shared access between the controllers

	/**
	 * Method for handling load requests and responses.
	 * 
	 * @param loadRequest LoadRequest
	 * @return LoadResponse object storing the load response.
	 */
	@PutMapping("/load")
	public LoadResponse load(@RequestBody LoadRequest loadRequest) { // The JSON body sent to this controller will be deserialized into an authorization request
		String requestedMsgID = loadRequest.getMessageId();
      	String requestedUserID = loadRequest.getUserId();
      	String requestedAmount = loadRequest.getTransactionAmount().getAmount();
      	String requestedCurrency = loadRequest.getTransactionAmount().getCurrency();
		DebitCredit debitOrCredit = loadRequest.getTransactionAmount().getDebitOrCredit();

		if (requestedMsgID.length() == 0) {
			throw new InternalException("The message ID is empty!");
		}
		else if (eventProcessor.eventExists(requestedMsgID)) { // Since non-correlated events tie to message IDs, we can check for their existance to determine if a message ID has already been used
			throw new InternalException("The message ID already exists!");
		}
		else if (requestedUserID.length() == 0) {
			throw new InternalException("The user ID is empty!");
		}
		else if (!Pattern.compile("^-?\\d*\\.?\\d+$").matcher(requestedAmount).find()) { // This regex pattern can be used to determine if the requested amount is non-numeric (contains letters or symbols other than . or -)
			throw new InternalException("The amount is non-numeric!");
		}
		else if (new BigDecimal(requestedAmount).compareTo(new BigDecimal(0)) != 1) { // This is equivalent to "if (requestedAmount == 0)"
			throw new InternalException("The amount is less than or equal to 0!");
		}
		else if (requestedCurrency.length() == 0) {
			throw new InternalException("The currency is empty!");
		}
		else if (!Pattern.compile("^[A-Z]{3}$").matcher(requestedCurrency).find()) { // This regex pattern can be used to determine if the requested currency follows the ISO 4217 standard of 3 uppercase letters
			throw new InternalException("The currency must be 3 uppercase letters!");
		}
		else if (debitOrCredit != DebitCredit.CREDIT) {
			throw new InternalException("For Load requests, please use CREDIT!");
		}

		LoadRequestEvent loadRequestEvent = new LoadRequestEvent(loadRequest.getMessageId(), loadRequest); // Create a load request event

		LoadResponse loadResponse = (LoadResponse)eventProcessor.processEvent(loadRequestEvent); // Pass the event into the event processor, where it will then return a load response

		LoadResponseEvent loadResponseEvent = new LoadResponseEvent(loadRequest.getMessageId(), loadResponse); // Create a load response event using the load response from the previous event

		eventProcessor.processEvent(loadResponseEvent); // Pass the event into the event processor; this will return a null as the LoadResponse class has no methods the event can call for processing

		return loadResponse; // Serialize the load response and send it back to the client
	}

}
