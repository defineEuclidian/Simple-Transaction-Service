package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;

/**
 * LoadResponseEvent class that is a child of the Event class.
 */
public class LoadResponseEvent extends Event {
  private LoadResponse loadResponse = null;

  /**
   * Parameterized constructor for LoadResponseEvent.
   * 
   * @param messageId String
   * @param loadResponse LoadResponse
   */
  public LoadResponseEvent(String messageId, LoadResponse loadResponse) {
    super(messageId); // Construct the LoadResponseEvent using the Event constructor
    this.loadResponse = loadResponse;
  }

  public LoadResponse getStoredRecord() {
    return loadResponse;
  }

  public Object process(HashMap<Pair<String, String>, BigDecimal> userBalances) {
    return null;
  }
}
