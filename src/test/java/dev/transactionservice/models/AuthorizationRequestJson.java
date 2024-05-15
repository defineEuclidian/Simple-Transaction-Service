package dev.transactionservice.models;

/**
 * AuthorizationRequestJson record that stores a String representation of the JSON body of an authorization request.
 * 
 * @param msgId String
 * @param userId String
 * @param amount String
 * @param currency String
 * @param debitOrCredit String
 */
public record AuthorizationRequestJson(String msgId, String userId, String amount, String currency, String debitOrCredit) {

    @Override
    public String toString() {
        return "{\"messageId\":\"" + msgId + "\",\"userId\":\"" + userId + "\",\"transactionAmount\":{\"amount\":\"" + amount + "\",\"currency\":\"" + currency + "\",\"debitOrCredit\":\"" + debitOrCredit + "\"}}";
    }
    
}
