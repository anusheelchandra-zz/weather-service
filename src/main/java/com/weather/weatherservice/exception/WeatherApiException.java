package com.weather.weatherservice.exception;

public class WeatherApiException extends RuntimeException {

  public WeatherApiException(Throwable cause) {
    super(cause);
  }

  public WeatherApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
