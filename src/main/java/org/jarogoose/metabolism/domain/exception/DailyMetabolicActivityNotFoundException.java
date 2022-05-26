package org.jarogoose.metabolism.domain.exception;

public class DailyMetabolicActivityNotFoundException extends RuntimeException {
  public DailyMetabolicActivityNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
