package com.weather.weatherservice.util;

import com.weather.weatherservice.domain.WeatherRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeatherApiUrlUtil {

  // TODO move it to application.yml
  private final String WEATHER_API_URL =
      "http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=0f960ddcf4b96b556ca9a39bdc473e89";

  private final String CITY_WITH_COUNTRYCODE = "%s,%s";

  public String getWeatherApiURI(WeatherRequest request) {
    if (request.getCountryCode() == null || request.getCountryCode().isEmpty()) {
      return String.format(WEATHER_API_URL, request.getCityName().toLowerCase());
    }
    String cityWithCountry =
        String.format(
            CITY_WITH_COUNTRYCODE,
            request.getCityName().toLowerCase(),
            request.getCountryCode().toLowerCase());
    return String.format(WEATHER_API_URL, cityWithCountry);
  }
}
