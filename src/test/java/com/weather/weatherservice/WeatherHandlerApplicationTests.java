package com.weather.weatherservice;

import com.weather.weatherservice.controller.WeatherController;
import com.weather.weatherservice.service.WeatherApiService;
import com.weather.weatherservice.service.WeatherHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherHandlerApplicationTests {

  @Autowired private WeatherController weatherController;
  @Autowired private WeatherHandler weatherHandler;
  @Autowired private WeatherApiService weatherApiService;

  @Test
  public void shouldLoadContext() {
    Assertions.assertThat(weatherController).isNotNull();
    Assertions.assertThat(weatherHandler).isNotNull();
    Assertions.assertThat(weatherApiService).isNotNull();
  }
}
