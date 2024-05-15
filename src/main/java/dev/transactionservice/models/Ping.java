package dev.transactionservice.models;

import java.time.Instant;

/**
 * Ping class that declares, initializes and stores an Instant time object, used to check the server time.
 */
public class Ping {
    private Instant serverTime = null;

    /**
     * Default constructor for Ping.
     */
    public Ping() {
      this.serverTime = Instant.now(); // Store the current UTC time
    }

    /**
     * Gets the current server time.
     * 
     * @return Instant object that stores the current server time.
     */
    public Instant getServerTime() {
      return serverTime;
    }
}
