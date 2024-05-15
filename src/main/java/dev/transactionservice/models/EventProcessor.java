package dev.transactionservice.models;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import java.math.BigDecimal;

/**
 * EventProcessor class for processing events that typically originate from the controllers.
 * 
 * <p> It is annotated with the Component annotation to allow for shared access in the controllers, when combined with the Autowired annotation. </p>
 */
@Component
public class EventProcessor {
  private HashSet<String> messageIds = null; // All logged message IDs
  private HashMap<Pair<String, String>, BigDecimal> userBalances = null; // All user balances across different currencies
  private Vector<Event> eventLogs = null; // All logged events

  /**
   * Default constructor for EventProcessor.
   */
  public EventProcessor() {
    this.messageIds = new HashSet<String>();
    this.userBalances = new HashMap<Pair<String, String>, BigDecimal>();
    this.eventLogs = new Vector<Event>();
  }

  /**
   * Logs and processes an event.
   * 
   * @param event Event
   * @return The return value of an event's process method as an Object.
   */
  public Object processEvent(Event event) {
    messageIds.add(event.getMessageId());
    eventLogs.add(event);
    
    return event.process(userBalances);
  }

  /**
   * Checks if an event exists in the event processor's internal log.
   * 
   * <p> Non-correlated events contain unique message IDs, so these IDs can be used to track their existence. </p>
   * 
   * @param messageId String
   * @return Boolean object that stores whether or not the event exists.
   */
  public Boolean eventExists(String messageId)
  {
    return messageIds.contains(messageId);
  }
  
  /**
   * Clears all stored data in the event controller.
   */
  public void clear()
  {
    messageIds.clear();
    userBalances.clear();
    eventLogs.clear();
  }
}
