package com.mikro.blog.payload;

import java.util.Date;

public class ErrorDetails {
  private Date timestamp;
  private String message;
  private String statusCode;

  public ErrorDetails(Date timestamp, String message, String statusCode) {
    this.timestamp = timestamp;
    this.message = message;
    this.statusCode = statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getStatusCode() {
    return statusCode;
  }
}
