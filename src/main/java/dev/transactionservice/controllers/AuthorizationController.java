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
 * AuthorizationController class to process authorization requests and responses through the /authorization path.
 * 
 * <p> Consequently, it removes funds from a user's account if sufficient funds are available. </p>
 */
@RestController
@ResponseStatus(HttpStatus.CREATED)
public class AuthorizationController {
	
	@Autowired
	private EventProcessor eventProcessor; // Autowired event processor to allow for shared access between the controllers

	/**
	 * Method for handling authorization requests and responses.
	 * 
	 * @param authorizationRequest AuthorizationRequest
	 * @return AuthorizationResponse object storing the authorization response.
	 */
	@PutMapping("/authorization")
	public AuthorizationResponse authorization(@RequestBody AuthorizationRequest authorizationRequest) { // The JSON body sent to this controller will be deserialized into an authorization request
		String requestedMsgID = authorizationRequest.getMessageId();
      	String requestedUserID = authorizationRequest.getUserId();
      	String requestedAmount = authorizationRequest.getTransactionAmount().getAmount();
      	String requestedCurrency = authorizationRequest.getTransactionAmount().getCurrency();
		DebitCredit debitOrCredit = authorizationRequest.getTransactionAmount().getDebitOrCredit();

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
		else if (debitOrCredit != DebitCredit.DEBIT) {
			throw new InternalException("For Authorization requests, please use DEBIT!");
		}

		AuthorizationRequestEvent authorizationRequestEvent = new AuthorizationRequestEvent(authorizationRequest.getMessageId(), authorizationRequest); // Create an authorization request event

		AuthorizationResponse authorizationResponse = (AuthorizationResponse)eventProcessor.processEvent(authorizationRequestEvent); // Pass the event into the event processor, where it will then return an authorization response

		AuthorizationResponseEvent authorizationResponseEvent = new AuthorizationResponseEvent(authorizationRequest.getMessageId(), authorizationResponse); // Create an authorization response event using the authorization response from the previous event

		eventProcessor.processEvent(authorizationResponseEvent); // Pass the event into the event processor; this will return a null as the AuthorizationResponse class has no methods the event can call for processing

		return authorizationResponse; // Serialize the authorization response and send it back to the client
	}

}
