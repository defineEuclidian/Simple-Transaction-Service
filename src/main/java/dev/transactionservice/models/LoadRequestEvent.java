package dev.transactionservice.models;

import java.util.HashMap;

import java.math.BigDecimal;

/**
 * LoadRequestEvent class that is a child of the Event class.
 */
public class LoadRequestEvent extends Event {
  private LoadRequest loadRequest = null;

  /**
   * Parameterized constructor for LoadRequestEvent.
   * 
   * @param messageId String
   * @param loadRequest LoadRequest
   */
  public LoadRequestEvent(String messageId, LoadRequest loadRequest) {
    super(messageId); // Construct the LoadRequestEvent using the Event constructor
    this.loadRequest = loadRequest;
  }

  public LoadRequest getStoredRecord() {
    return loadRequest;
  }

  public Object process(HashMap<Pair<String, String>, BigDecimal> userBalances) {
    return loadRequest.processRequest(this, userBalances);
  }
}
