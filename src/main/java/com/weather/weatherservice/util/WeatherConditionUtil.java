package com.weather.weatherservice.util;

import com.weather.weatherservice.domain.RainCondition;
import com.weather.weatherservice.entity.WeatherCondition;
import java.util.Set;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeatherConditionUtil {

  public boolean isRainCondition(Set<WeatherCondition> weatherConditions) {
    return weatherConditions.stream()
        .map(WeatherCondition::getDescription)
        .anyMatch(WeatherConditionUtil::isRainCondition);
  }

  private boolean isRainCondition(String description) {
    return Stream.of(RainCondition.values())
        .map(Enum::name)
        .anyMatch(rainCondition -> rainCondition.equalsIgnoreCase(description));
  }
}
