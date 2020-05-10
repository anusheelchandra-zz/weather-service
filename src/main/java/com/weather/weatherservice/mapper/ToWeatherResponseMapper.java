package com.weather.weatherservice.mapper;

import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherDetails;
import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ToWeatherResponseMapper {

  public WeatherResponse toWeatherResponse(WeatherApiResponse apiResponse) {
    return WeatherResponse.builder()
        .cityName(apiResponse.getName().toLowerCase())
        .countryCode(apiResponse.getSys().getCountry().toLowerCase())
        .temperature(apiResponse.getMain().getTemp())
        .pressure(apiResponse.getMain().getPressure())
        .created(LocalDateTime.now())
        .condition(buildWeatherConditions(apiResponse))
        .build();
  }

  private Set<WeatherCondition> buildWeatherConditions(WeatherApiResponse apiResponse) {
    List<String> conditionsList =
        apiResponse.getWeather().stream().map(WeatherDetails::getMain).collect(Collectors.toList());

    return conditionsList.stream()
        .map(condition -> WeatherCondition.builder().description(condition).build())
        .collect(Collectors.toSet());
  }
}
