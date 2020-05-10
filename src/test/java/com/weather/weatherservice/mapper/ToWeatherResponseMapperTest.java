package com.weather.weatherservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.entity.WeatherResponse;
import java.io.IOException;
import java.io.InputStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToWeatherResponseMapperTest {

  @Test
  public void shouldMapToWeatherResponse() throws IOException {
    WeatherResponse weatherResponse =
        ToWeatherResponseMapper.toWeatherResponse(getWeatherResponse());
    Assertions.assertThat(weatherResponse).isNotNull();
    Assertions.assertThat(weatherResponse.getCityName()).isEqualTo("london");
    Assertions.assertThat(weatherResponse.getCountryCode()).isEqualTo("gb");
    Assertions.assertThat(weatherResponse.getPressure()).isEqualTo(Float.valueOf(1008f));
    Assertions.assertThat(weatherResponse.getTemperature()).isEqualTo(Float.valueOf(291.31f));

    Assertions.assertThat(weatherResponse.getCondition()).isNotEmpty();
    Assertions.assertThat(weatherResponse.getCondition().size()).isEqualTo(1);
    Assertions.assertThat(weatherResponse.getCondition().iterator().next().getDescription())
        .isEqualTo("Clouds");
  }

  private WeatherApiResponse getWeatherResponse() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("/weatherApiResponse.json");
    return new ObjectMapper().readValue(inputStream, WeatherApiResponse.class);
  }
}
