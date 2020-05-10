package com.weather.weatherservice.mapper;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToCurrentWeatherResponseMapperTest {

  @Test
  public void shouldMapToCurrentWeatherREsponse() {
    CurrentWeatherResponse currentWeatherResponse =
        ToCurrentWeatherResponseMapper.toCurrentWeatherResponse(getWeatherResponse());
    Assertions.assertThat(currentWeatherResponse).isNotNull();
    Assertions.assertThat(currentWeatherResponse.getUmbrella()).isTrue();
    Assertions.assertThat(currentWeatherResponse.getPressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(currentWeatherResponse.getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(currentWeatherResponse.getCreated()).isInstanceOf(LocalDateTime.class);
  }

  private WeatherResponse getWeatherResponse() {
    return WeatherResponse.builder()
        .cityName("london")
        .countryCode("gb")
        .temperature(1001.0f)
        .pressure(1201.0f)
        .created(LocalDateTime.now())
        .condition(Collections.singleton(WeatherCondition.builder().description("Rain").build()))
        .build();
  }
}
