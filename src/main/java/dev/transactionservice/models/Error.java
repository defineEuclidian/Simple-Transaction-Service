package dev.transactionservice.models;

import java.time.Instant;

/**
 * Error class for storing a customized HTTP error response message, to go with the customized error controller.
 */
public class Error {
  private String message = null;
  private String code = null;
  private Instant timeStamp = null;
  private String path = null;
  private String reason = null;

  /**
   * Default constructor for Error.
   */
  public Error() {
    this.timeStamp = Instant.now(); // Store the current UTC time
  }

  /**
   * Parameterized constructor for Error.
   * 
   * @param message String
   * @param code String
   * @param path String
   * @param reason String
   */
  public Error(String message, String code, String path, String reason) {
    this.message = message;
    this.code = code;
    this.timeStamp = Instant.now();
    this.path = path;
    this.reason = reason;
  }

  /**
   * Sets the HTTP error response message.
   * 
   * @param message String
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Sets the HTTP error response code.
   * 
   * @param code String
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Sets the errored path.
   * 
   * @param path String
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Sets the error reason, such as from an exception.
   * 
   * @param reason String
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * Gets the HTTP error response message.
   * 
   * @return String object containing the HTTP error response message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the HTTP error response code.
   * 
   * @return String object containing the HTTP error response code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the error time stamp.
   * 
   * @return String object containing the error time stamp.
   */
  public Instant getTimeStamp() {
    return timeStamp;
  }

  /**
   * Gets the errored path.
   * 
   * @return String object containing the errored path.
   */
  public String getPath() {
    return path;
  }

  /**
   * Gets the error reason, such as from an exception.
   * 
   * @return String object containing the error reason.
   */
  public String getReason() {
    return reason;
  }
}
