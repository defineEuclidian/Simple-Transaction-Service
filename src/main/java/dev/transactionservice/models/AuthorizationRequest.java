package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * AuthorizationRequest class for storing an authorization request that needs to be processed.
 */
public class AuthorizationRequest {
    private String messageId = null;
    private String userId = null;
    private Amount transactionAmount = null;

    /**
     * Default constructor for AuthorizationRequest.
     */
    public AuthorizationRequest() {
      
    }

    /**
     * Parameterized constructor for AuthorizationRequest.
     * 
     * @param messageId String
     * @param userId String
     * @param transactionAmount Amount
     */
    public AuthorizationRequest(String messageId, String userId, Amount transactionAmount) {
      this.messageId = messageId;
      this.userId = userId;
      this.transactionAmount = transactionAmount;
    }

    /**
     * Sets the message ID.
     * 
     * @param messageId String
     */
    public void setMessageId(String messageId) {
      this.messageId = messageId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId String
     */
    public void setUserId(String userId) {
      this.userId = userId;
    }

    /**
     * Sets the transaction amount.
     * 
     * @param transactionAmount Amount
     */
    public void setTransactionAmount(Amount transactionAmount) {
      this.transactionAmount = transactionAmount;
    }

    /**
     * Gets the message ID.
     * 
     * @return String object containing the message ID.
     */
    public String getMessageId() {
      return messageId;
    }

    /**
     * Gets the user ID.
     * 
     * @return String object containing the user ID.
     */
    public String getUserId() {
      return userId;
    }

    /**
     * Gets the transaction amount.
     * 
     * @return Amount object containing the transaction amount.
     */
    public Amount getTransactionAmount() {
      return transactionAmount;
    }

    /**
     * Creates an authorization response from an authorization request event.
     * 
     * @param authorizationRequestEvent AuthorizationRequestEvent
     * @param userBalances HashMap&lt;Pair&lt;String, String&gt;, BigDecimal&gt;
     * @return AuthorizationResponse object containing the response of an authorization request following an authorization request event.
     */
    public AuthorizationResponse processRequest(AuthorizationRequestEvent authorizationRequestEvent, HashMap<Pair<String, String>, BigDecimal> userBalances) {
      String requestedMsgID = authorizationRequestEvent.getStoredRecord().getMessageId();
      String requestedUserID = authorizationRequestEvent.getStoredRecord().getUserId();
      String requestedAmount = authorizationRequestEvent.getStoredRecord().getTransactionAmount().getAmount();
      String requestedCurrency = authorizationRequestEvent.getStoredRecord().getTransactionAmount().getCurrency();
      DebitCredit debitOrCredit = authorizationRequestEvent.getStoredRecord().getTransactionAmount().getDebitOrCredit();
      ResponseCode responseCode = ResponseCode.APPROVED;

      BigDecimal requestedAmountNum = new BigDecimal(requestedAmount).setScale(2, RoundingMode.FLOOR); // Truncate the amount to two decimal places
      BigDecimal currentUserBalance = new BigDecimal(0).setScale(2); // We need to return a 0.00 as the amount instead of a 0 if the need arises

      Pair<String, String> userIdCurrency = new Pair<>(requestedUserID, requestedCurrency);

      // Does the user ID and associated currency balance exist, and does subtracting the amount from the balance result in a greater than or equal to 0 balance?
      if (userBalances.containsKey(userIdCurrency) && userBalances.get(userIdCurrency).subtract(requestedAmountNum).compareTo(currentUserBalance) >= 0) {
          
        userBalances.put(userIdCurrency, userBalances.get(userIdCurrency).subtract(requestedAmountNum)); // Subtract the amount from the balance
        currentUserBalance = userBalances.get(userIdCurrency); // Retrieve the now modified balance

      } // Does the user ID and associated currency balance exist?
      else if (userBalances.containsKey(userIdCurrency)) {

        currentUserBalance = userBalances.get(userIdCurrency); // Retrieve the unmodified balance following a failed authorization
        responseCode = ResponseCode.DECLINED;

      }
      else {

        responseCode = ResponseCode.DECLINED;

      }

      Amount balance = new Amount(currentUserBalance.toString(), requestedCurrency, debitOrCredit);

      AuthorizationResponse authorizationResponse = new AuthorizationResponse(requestedMsgID, requestedUserID, responseCode, balance);

      return authorizationResponse;
    }

    @Override
    public String toString() {
        return "{\"messageId\":\"" + messageId + "\",\"userId\":\"" + userId + "\",\"transactionAmount\":" + transactionAmount.toString();
    }
}
