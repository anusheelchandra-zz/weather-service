package com.weather.weatherservice.mapper;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.util.WeatherConditionUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ToCurrentWeatherResponseMapper {

  public CurrentWeatherResponse toCurrentWeatherResponse(WeatherResponse weatherResponse) {
    return CurrentWeatherResponse.builder()
        .temperature(weatherResponse.getTemperature())
        .pressure(weatherResponse.getPressure())
        .umbrella(WeatherConditionUtil.isRainCondition(weatherResponse.getCondition()))
        .created(weatherResponse.getCreated())
        .build();
  }
}
