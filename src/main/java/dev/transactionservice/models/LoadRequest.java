package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * LoadRequest class for storing a load request that needs to be processed.
 */
public class LoadRequest {
    private String messageId = null;
    private String userId = null;
    private Amount transactionAmount = null;

    /**
     * Default constructor for LoadRequest.
     */
    public LoadRequest() {
      
    }

    /**
     * Parameterized constructor for LoadRequest.
     * 
     * @param messageId String
     * @param userId String
     * @param transactionAmount Amount
     */
    public LoadRequest(String messageId, String userId, Amount transactionAmount) {
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
     * Creates a load response from a load request event.
     * 
     * @param loadRequestEvent LoadRequestEvent
     * @param userBalances HashMap&lt;Pair&lt;String, String&gt;, BigDecimal&gt;
     * @return LoadResponse object containing the response of a load request following a load request event.
     */
    public LoadResponse processRequest(LoadRequestEvent loadRequestEvent, HashMap<Pair<String, String>, BigDecimal> userBalances) {
      String requestedMsgID = loadRequestEvent.getStoredRecord().getMessageId();
      String requestedUserID = loadRequestEvent.getStoredRecord().getUserId();
      String requestedAmount = loadRequestEvent.getStoredRecord().getTransactionAmount().getAmount();
      String requestedCurrency = loadRequestEvent.getStoredRecord().getTransactionAmount().getCurrency();
      DebitCredit debitOrCredit = loadRequestEvent.getStoredRecord().getTransactionAmount().getDebitOrCredit();
      ResponseCode responseCode = ResponseCode.APPROVED;

      BigDecimal requestedAmountNum = new BigDecimal(requestedAmount).setScale(2, RoundingMode.FLOOR); // Truncate the amount to two decimal places
      BigDecimal currentUserBalance = new BigDecimal(0); // We need to return a 0.00 as the amount instead of a 0 if the need arises

      Pair<String, String> userIdCurrency = new Pair<>(requestedUserID, requestedCurrency);

      // Does the user ID and associated currency balance exist?
      if (userBalances.containsKey(userIdCurrency)) {

        userBalances.put(userIdCurrency, userBalances.get(userIdCurrency).add(requestedAmountNum)); // Add the amount to the balance

      }
      else {

        userBalances.put(userIdCurrency, requestedAmountNum); // Add the amount; this is now a new balance

      }

      currentUserBalance = userBalances.get(userIdCurrency); // Retrieve the now modified or new balance

      Amount balance = new Amount(currentUserBalance.toString(), requestedCurrency, debitOrCredit);
      
      LoadResponse loadResponse = new LoadResponse(requestedMsgID, requestedUserID, responseCode, balance);

      return loadResponse;
    }

    @Override
    public String toString() {
        return "{\"messageId\":\"" + messageId + "\",\"userId\":\"" + userId + "\",\"transactionAmount\":" + transactionAmount.toString();
    }
}
