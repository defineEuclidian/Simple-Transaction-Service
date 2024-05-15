package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;

/**
 * AuthorizationResponseEvent class that is a child of the Event class.
 */
public class AuthorizationResponseEvent extends Event {
  private AuthorizationResponse authorizationResponse = null;

  /**
   * Parameterized constructor for AuthorizationResponseEvent.
   * 
   * @param messageId String
   * @param authorizationResponse AuthorizationResponse
   */
  public AuthorizationResponseEvent(String messageId, AuthorizationResponse authorizationResponse) {
    super(messageId); // Construct the AuthorizationResponseEvent using the Event constructor
    this.authorizationResponse = authorizationResponse;
  }

  public AuthorizationResponse getStoredRecord() {
    return authorizationResponse;
  }

  public Object process(HashMap<Pair<String, String>, BigDecimal> userBalances) {
    return null;
  }
}
