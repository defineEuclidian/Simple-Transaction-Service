package dev.transactionservice.models;

import java.time.Instant;

import java.util.HashMap;

import java.math.BigDecimal;

/**
 * Event abstract class for storing an event, in accordance with the Event Sourcing model.
 * 
 * <p> There are no setter functions, as Event and its child classes are designed to be immutable. </p>
 */
public abstract class Event {
  private String messageId = null;
  private Instant timeStamp = null;

  /**
   * Parameterized constructor for Event.
   * 
   * @param messageId String
   */
  public Event(String messageId) {
    this.messageId = messageId;
    this.timeStamp = Instant.now();
  }

  /**
   * Gets the message ID, sourced from the message ID of a request or response.
   * 
   * @return String object containing the message ID.
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * Gets the event time stamp.
   * 
   * @return String object containing the event time stamp.
   */
  public Instant getTimeStamp() {
    return timeStamp;
  }

  /**
   * Abstract method that gets the stored record in an event.
   * 
   * @return The stored record as an Object.
   */
  public abstract Object getStoredRecord();

  /**
   * Abstract method that returns some Object, typically an Object returned from the method function processRequest in a request class.
   * 
   * <p> An example would be calling processRequest on a LoadRequest object, which would return a LoadResponse object. </p>
   * 
   * @param userBalances HashMap&lt;Pair&lt;String, String&gt;, BigDecimal&gt;
   * @return Some returned value as an Object.
   */
  public abstract Object process(HashMap<Pair<String, String>, BigDecimal> userBalances);
}
