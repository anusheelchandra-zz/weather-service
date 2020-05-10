package com.weather.weatherservice.util;

import com.weather.weatherservice.domain.WeatherRequest;
import javax.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class WeatherRequestFactory {

  public WeatherRequest createWeatherRequest(String location) {
    if (location != null && !location.isEmpty()) {
      String[] cityAndCountryCode = location.split(",");
      if (cityAndCountryCode.length == 1) {
        return WeatherRequest.builder().cityName(cityAndCountryCode[0].toLowerCase()).build();
      }
      if (cityAndCountryCode.length == 2) {
        return WeatherRequest.builder()
            .cityName(cityAndCountryCode[0].toLowerCase())
            .countryCode(cityAndCountryCode[1].toLowerCase())
            .build();
      }
    }
    throw new ValidationException("Required String parameter 'location' is not valid");
  }
}
