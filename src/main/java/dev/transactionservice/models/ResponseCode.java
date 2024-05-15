package dev.transactionservice.models;

/**
 * The response code sent back to the network for the merchant. Multiple declines
    reasons may exist but only one will be sent back to the network. Advice messages
    will include the response code that was sent on our behalf.
 */
public enum ResponseCode {
    APPROVED,
    DECLINED
}
