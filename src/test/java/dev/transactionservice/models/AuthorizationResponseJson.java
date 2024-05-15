package dev.transactionservice.models;

/**
 * AuthorizationResponseJson record that stores a String representation of the JSON body of an authorization response.
 * 
 * @param msgId String
 * @param userId String
 * @param amount String
 * @param responseCode String
 * @param currency String
 * @param debitOrCredit String
 */
public record AuthorizationResponseJson(String msgId, String userId, String responseCode, String amount, String currency, String debitOrCredit) {

    @Override
    public String toString() {
        return "{\"messageId\":\"" + msgId + "\",\"userId\":\"" + userId + "\",\"responseCode\":\"" + responseCode + "\",\"balance\":{\"amount\":\"" + amount + "\",\"currency\":\"" + currency + "\",\"debitOrCredit\":\"" + debitOrCredit + "\"}}";
    }
    
}
