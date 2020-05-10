package com.weather.weatherservice.service;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.util.WeatherRequestFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class WeatherHandlerTest {

  @Mock private WeatherApiService weatherApiService;
  @Mock private HistoricWeatherService historicWeatherService;

  private WeatherRequestFactory requestFactory;

  private WeatherHandler weatherHandler;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    weatherHandler =
        new WeatherHandler(new WeatherRequestFactory(), weatherApiService, historicWeatherService);

    Mockito.when(weatherApiService.getCurrentWeather(ArgumentMatchers.any()))
        .thenReturn(Optional.of(getWeatherResponse()));

    Mockito.when(historicWeatherService.getHistoricWeather(ArgumentMatchers.any()))
        .thenReturn(buildHistoricResponse());
  }

  @Test
  public void shouldGetCurrentWeatherByCity() {
    CurrentWeatherResponse currentWeather = weatherHandler.getCurrentWeatherByLocation("london");
    Assertions.assertThat(currentWeather).isNotNull();
    commonAssertion(currentWeather);
  }

  @Test
  public void shouldGetCurrentWeatherByCityAndCountry() {
    CurrentWeatherResponse currentWeather = weatherHandler.getCurrentWeatherByLocation("london,gb");
    Assertions.assertThat(currentWeather).isNotNull();
    commonAssertion(currentWeather);
  }

  @Test
  public void shouldGetHistoricWeatherByCity() {
    HistoricWeatherResponse historicWeather = weatherHandler.getHistoricWeatherByLocation("london");
    Assertions.assertThat(historicWeather.getAverageTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getAveragePressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(historicWeather.getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeather.getHistory().size()).isEqualTo(1);
    commonAssertion(historicWeather.getHistory().get(0));
  }

  @Test
  public void shouldGetHistoricWeatherByCityAndCountry() {
    HistoricWeatherResponse historicWeather =
        weatherHandler.getHistoricWeatherByLocation("london,gb");
    Assertions.assertThat(historicWeather.getAverageTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(historicWeather.getAveragePressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(historicWeather.getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeather.getHistory().size()).isEqualTo(1);
    commonAssertion(historicWeather.getHistory().get(0));
  }

  private void commonAssertion(CurrentWeatherResponse currentWeather) {
    Assertions.assertThat(currentWeather.getUmbrella()).isTrue();
    Assertions.assertThat(currentWeather.getPressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(currentWeather.getTemperature()).isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(currentWeather.getCreated()).isInstanceOf(LocalDateTime.class);
  }

  private Optional<HistoricWeatherResponse> buildHistoricResponse() {
    return Optional.ofNullable(
        HistoricWeatherResponse.builder()
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
            .build());
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
