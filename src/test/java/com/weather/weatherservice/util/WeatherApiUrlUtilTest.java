package com.weather.weatherservice.util;

import com.weather.weatherservice.domain.WeatherRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherApiUrlUtilTest {

  @Test
  public void shouldGetUrlForCity() {
    String url =
        WeatherApiUrlUtil.getWeatherApiURI(WeatherRequest.builder().cityName("London").build());
    Assertions.assertThat(url).isNotEmpty();
    Assertions.assertThat(url).contains("london");
  }

  @Test
  public void shouldGetUrlForCityAndCountry() {
    String url =
        WeatherApiUrlUtil.getWeatherApiURI(
            WeatherRequest.builder().cityName("London").countryCode("GB").build());
    Assertions.assertThat(url).isNotEmpty();
    Assertions.assertThat(url).contains("london");
    Assertions.assertThat(url).contains("gb");
  }
}
