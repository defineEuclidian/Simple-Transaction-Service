package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;

/**
 * AuthorizationRequestEvent class that is a child of the Event class.
 */
public class AuthorizationRequestEvent extends Event {
  private AuthorizationRequest authorizationRequest = null;

  /**
   * Parameterized constructor for AuthorizationRequestEvent.
   * 
   * @param messageId String
   * @param authorizationRequest AuthorizationRequest
   */
  public AuthorizationRequestEvent(String messageId, AuthorizationRequest authorizationRequest) {
    super(messageId); // Construct the AuthorizationRequestEvent using the Event constructor
    this.authorizationRequest = authorizationRequest;
  }

  public AuthorizationRequest getStoredRecord() {
    return authorizationRequest;
  }

  public Object process(HashMap<Pair<String, String>, BigDecimal> userBalances) {
    return authorizationRequest.processRequest(this, userBalances);
  }
}
