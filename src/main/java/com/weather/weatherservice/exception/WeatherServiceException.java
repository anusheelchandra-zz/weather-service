package com.weather.weatherservice.exception;

public class WeatherServiceException extends RuntimeException {

  public WeatherServiceException(Throwable cause) {
    super(cause);
  }

  public WeatherServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
