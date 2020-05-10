package com.weather.weatherservice.util;

import com.weather.weatherservice.entity.WeatherCondition;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherConditionUtilTest {

  @Test
  public void shouldReturnFalseWhenWeatherConditionIsCloud() {
    boolean isRainCondition = WeatherConditionUtil.isRainCondition(getWeatherCondition("Clouds"));
    Assertions.assertThat(isRainCondition).isFalse();
  }

  @Test
  public void shouldReturnTrueWhenWeatherConditionIsRain() {
    boolean isRainCondition = WeatherConditionUtil.isRainCondition(getWeatherCondition("Rain"));
    Assertions.assertThat(isRainCondition).isTrue();
  }

  @Test
  public void shouldReturnTrueWhenWeatherConditionIsDrizzle() {
    boolean isRainCondition = WeatherConditionUtil.isRainCondition(getWeatherCondition("Drizzle"));
    Assertions.assertThat(isRainCondition).isTrue();
  }

  @Test
  public void shouldReturnTrueWhenWeatherConditionIsThunderstorm() {
    boolean isRainCondition =
        WeatherConditionUtil.isRainCondition(getWeatherCondition("Thunderstorm"));
    Assertions.assertThat(isRainCondition).isTrue();
  }

  private Set<WeatherCondition> getWeatherCondition(String description) {
    return Set.of(WeatherCondition.builder().description(description).build());
  }
}
