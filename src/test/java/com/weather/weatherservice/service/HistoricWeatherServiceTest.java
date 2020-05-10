package com.weather.weatherservice.service;

import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.exception.WeatherServiceException;
import com.weather.weatherservice.repository.WeatherResponseRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class HistoricWeatherServiceTest {

  @Mock private WeatherResponseRepository repository;

  private HistoricWeatherService historicWeatherService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    historicWeatherService = new HistoricWeatherService(repository);

    Mockito.when(repository.findByCityName("london")).thenReturn(buildWeatherResponse());

    Mockito.when(repository.findByCityNameAndCountryCode("london", "gb"))
        .thenReturn(buildWeatherResponse());
  }

  @Test
  public void shouldGetHistoricWeatherByCityName() {
    WeatherRequest request = WeatherRequest.builder().cityName("london").build();
    Optional<HistoricWeatherResponse> weather = historicWeatherService.getHistoricWeather(request);
    Assertions.assertThat(weather).isPresent();
    commonAssertions(weather.get());
  }

  @Test
  public void shouldGetHistoricWeatherByCityNameAndCountryCode() {
    WeatherRequest request = WeatherRequest.builder().cityName("london").build();
    Optional<HistoricWeatherResponse> weather = historicWeatherService.getHistoricWeather(request);
    Assertions.assertThat(weather).isPresent();
    commonAssertions(weather.get());
  }

  @Test
  public void shouldReturnEmptyWhenTNoWeatherResponseInDBForCity() {
    WeatherRequest request = WeatherRequest.builder().cityName("berlin").build();
    Optional<HistoricWeatherResponse> weather = historicWeatherService.getHistoricWeather(request);
    Assertions.assertThat(weather).isEmpty();
  }

  @Test
  public void shouldReturnEmptyWhenNoWeatherResponseInDBForCountry() {
    WeatherRequest request = WeatherRequest.builder().cityName("london").countryCode("us").build();
    Optional<HistoricWeatherResponse> weather = historicWeatherService.getHistoricWeather(request);
    Assertions.assertThat(weather).isEmpty();
  }

  @Test
  public void shouldReturnEmptyWhenNoWeatherResponseInDBForCityAndCountry() {
    WeatherRequest request = WeatherRequest.builder().cityName("boston").countryCode("us").build();
    Optional<HistoricWeatherResponse> weather = historicWeatherService.getHistoricWeather(request);
    Assertions.assertThat(weather).isEmpty();
  }

  @Test
  public void shouldThrowExceptionWhenSomethingGoesWrongForCityAndCountry() {
    Mockito.when(repository.findByCityNameAndCountryCode("boston", "us"))
        .thenThrow(
            new WeatherServiceException("Error while getting historic weather", new Throwable()));

    WeatherRequest request = WeatherRequest.builder().cityName("boston").countryCode("us").build();

    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            WeatherServiceException.class,
            () -> {
              historicWeatherService.getHistoricWeather(request);
            });
    Assertions.assertThat(exception.getMessage()).isEqualTo("Error while getting historic weather");
  }

  @Test
  public void shouldThrowExceptionWhenSomethingGoesWrongForCity() {
    Mockito.when(repository.findByCityName("boston"))
        .thenThrow(
            new WeatherServiceException("Error while getting historic weather", new Throwable()));

    WeatherRequest request = WeatherRequest.builder().cityName("boston").build();

    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            WeatherServiceException.class,
            () -> {
              historicWeatherService.getHistoricWeather(request);
            });
    Assertions.assertThat(exception.getMessage()).isEqualTo("Error while getting historic weather");
  }

  private void commonAssertions(HistoricWeatherResponse weatherResponses) {
    Assertions.assertThat(weatherResponses.getAveragePressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(weatherResponses.getAverageTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(weatherResponses.getHistory().get(0).getPressure())
        .isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(weatherResponses.getHistory().get(0).getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(weatherResponses.getHistory().get(0).getUmbrella()).isTrue();
  }

  private List<WeatherResponse> buildWeatherResponse() {
    return List.of(
        WeatherResponse.builder()
            .cityName("london")
            .countryCode("gb")
            .temperature(1001.0f)
            .pressure(1201.0f)
            .created(LocalDateTime.now())
            .condition(
                Collections.singleton(WeatherCondition.builder().description("Rain").build()))
            .build());
  }
}
