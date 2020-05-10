package com.weather.weatherservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherservice.client.WeatherApiClient;
import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.exception.WeatherApiException;
import com.weather.weatherservice.mapper.ToWeatherResponseMapper;
import com.weather.weatherservice.repository.WeatherResponseRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WeatherApiServiceTest {

  @Mock private WeatherApiClient weatherApiClient;
  @Mock private WeatherResponseRepository repository;

  private WeatherApiService weatherApiService;

  @BeforeEach
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    weatherApiService = new WeatherApiService(weatherApiClient, repository);

    WeatherApiResponse weatherApiResponse = getWeatherResponse();
    WeatherResponse weatherResponse = ToWeatherResponseMapper.toWeatherResponse(weatherApiResponse);
    ResponseEntity<WeatherApiResponse> responseEntity =
        new ResponseEntity<>(weatherApiResponse, HttpStatus.OK);

    Mockito.when(weatherApiClient.getWeatherDetails(ArgumentMatchers.any()))
        .thenReturn(responseEntity);

    Mockito.when(repository.save(Mockito.any(WeatherResponse.class))).thenReturn(weatherResponse);
  }

  @Test
  public void shouldGetCurrentWeatherForCity() {
    WeatherRequest request = WeatherRequest.builder().cityName("london").build();
    Optional<WeatherResponse> currentWeather = weatherApiService.getCurrentWeather(request);
    Assertions.assertThat(currentWeather).isPresent();
    commonAssertion(currentWeather.get());
  }

  @Test
  public void shouldGetCurrentWeatherForCityAndCountry() {
    WeatherRequest request = WeatherRequest.builder().cityName("london").countryCode("gb").build();
    Optional<WeatherResponse> currentWeather = weatherApiService.getCurrentWeather(request);
    Assertions.assertThat(currentWeather).isPresent();
    commonAssertion(currentWeather.get());
  }

  @Test
  public void shouldThrowException() {
    Mockito.when(repository.save(Mockito.any(WeatherResponse.class)))
        .thenThrow(new WeatherApiException("Something went wrong", new Throwable()));

    WeatherRequest request = WeatherRequest.builder().cityName("london").countryCode("gb").build();

    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            WeatherApiException.class,
            () -> {
              weatherApiService.getCurrentWeather(request);
            });
    Assertions.assertThat(exception.getMessage()).isEqualTo("Something went wrong");
  }

  private void commonAssertion(WeatherResponse weatherResponse) {
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
