package com.weather.weatherservice.controller;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.service.WeatherHandler;
import java.time.LocalDateTime;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WeatherControllerTest {

  @Mock private WeatherHandler weatherHandler;

  private WeatherController weatherController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    weatherController = new WeatherController(weatherHandler);

    Mockito.when(weatherHandler.getCurrentWeatherByLocation(ArgumentMatchers.anyString()))
        .thenReturn(buildCurrentWeather());

    Mockito.when(weatherHandler.getHistoricWeatherByLocation(ArgumentMatchers.anyString()))
        .thenReturn(buildHistoricResponse());
  }

  @Test
  public void shouldGetCurrentWeatherForCity() {
    ResponseEntity<CurrentWeatherResponse> currentWeather =
        weatherController.getCurrentWeather("london");
    Assertions.assertThat(currentWeather).isNotNull();
    Assertions.assertThat(currentWeather.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(currentWeather.getBody()).isNotNull();
    Assertions.assertThat(currentWeather.getBody().getUmbrella()).isTrue();
    Assertions.assertThat(currentWeather.getBody().getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(currentWeather.getBody().getPressure()).isEqualTo(Float.valueOf(1201.0f));
  }

  @Test
  public void shouldGetCurrentWeatherForCityAndCountry() {
    ResponseEntity<CurrentWeatherResponse> currentWeather =
        weatherController.getCurrentWeather("london,gb");
    Assertions.assertThat(currentWeather).isNotNull();
    Assertions.assertThat(currentWeather.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(currentWeather.getBody()).isNotNull();
    Assertions.assertThat(currentWeather.getBody().getUmbrella()).isTrue();
    Assertions.assertThat(currentWeather.getBody().getPressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(currentWeather.getBody().getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
  }

  @Test
  public void shouldGetHistoricWeatherForCity() {
    ResponseEntity<HistoricWeatherResponse> historicWeather =
        weatherController.getHistoricWeather("london");
    Assertions.assertThat(historicWeather).isNotNull();
    Assertions.assertThat(historicWeather.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(historicWeather.getBody()).isNotNull();
    Assertions.assertThat(historicWeather.getBody().getAverageTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getBody().getAveragePressure())
        .isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(historicWeather.getBody().getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeather.getBody().getHistory().size()).isEqualTo(1);
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getUmbrella()).isTrue();
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getPressure())
        .isEqualTo(Float.valueOf(1201.0f));
  }

  @Test
  public void shouldGetHistoricWeatherForCityAndCountry() {
    ResponseEntity<HistoricWeatherResponse> historicWeather =
        weatherController.getHistoricWeather("london,gb");
    Assertions.assertThat(historicWeather).isNotNull();
    Assertions.assertThat(historicWeather.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(historicWeather.getBody()).isNotNull();
    Assertions.assertThat(historicWeather.getBody().getAverageTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getBody().getAveragePressure())
        .isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(historicWeather.getBody().getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeather.getBody().getHistory().size()).isEqualTo(1);
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getUmbrella()).isTrue();
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getBody().getHistory().get(0).getPressure())
        .isEqualTo(Float.valueOf(1201.0f));
  }

  private CurrentWeatherResponse buildCurrentWeather() {
    return CurrentWeatherResponse.builder()
        .temperature(1001.0f)
        .pressure(1201.0f)
        .umbrella(true)
        .created(LocalDateTime.now())
        .build();
  }

  private HistoricWeatherResponse buildHistoricResponse() {
    return HistoricWeatherResponse.builder()
        .averagePressure(1201.0f)
        .averageTemperature(1001.0f)
        .history(
            Collections.singletonList(
                CurrentWeatherResponse.builder()
                    .pressure(1201.0f)
                    .temperature(1001.0f)
                    .umbrella(true)
                    .created(LocalDateTime.now())
                    .build()))
        .build();
  }
}
