package dev.transactionservice.models;

/**
 * AuthorizationResponse class for storing the result of an authorization.
 */
public class AuthorizationResponse {
    private String messageId = null;
    private String userId = null;
    private ResponseCode responseCode = null;
    private Amount balance = null;

    /**
     * Default constructor for AuthorizationResponse.
     */
    public AuthorizationResponse() {
      
    }

    /**
     * Parameterized constructor for AuthorizationResponse.
     * 
     * @param messageId String
     * @param userId String
     * @param responseCode ResponseCode
     * @param balance Amount
     */
    public AuthorizationResponse(String messageId, String userId, ResponseCode responseCode, Amount balance) {
      this.messageId = messageId;
      this.userId = userId;
      this.responseCode = responseCode;
      this.balance = balance;
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
     * Sets the response code.
     * 
     * @param responseCode ResponseCode
     */
    public void setResponseCode(ResponseCode responseCode) {
      this.responseCode = responseCode;
    }

    /**
     * Sets the balance amount.
     * 
     * @param balance Amount
     */
    public void setBalance(Amount balance) {
      this.balance = balance;
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
     * Gets the response code.
     * 
     * @return ResponseCode enumeration as either APPROVED or DECLINED.
     */
    public ResponseCode getResponseCode() {
      return responseCode;
    }

    /**
     * Gets the balance amount.
     * 
     * @return Amount object containing the balance amount.
     */
    public Amount getBalance() {
      return balance;
    }

    @Override
    public String toString() {
        return "{\"messageId\":\"" + messageId + "\",\"userId\":\"" + userId + "\",\"responseCode\":\"" + responseCode + "\",\"balance\":" + balance.toString();
    }
}
