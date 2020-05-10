package com.weather.weatherservice.client;

import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

/* Ignoring this Integration Test from test suite because it depends on third part api, but can be run manually or by
removing @Disable */
@Disabled
@SpringBootTest
public class WeatherApiClientIT {

  @Autowired private WeatherApiClient apiClient;

  @Test
  public void shouldGetWeatherDataByCity() {
    WeatherRequest request = WeatherRequest.builder().cityName("London").build();
    ResponseEntity<WeatherApiResponse> entity = apiClient.getWeatherDetails(request);
    commonAssertion(entity);
  }

  @Test
  public void shouldGetWeatherDataByCityAndCountry() {
    WeatherRequest request = WeatherRequest.builder().cityName("London").countryCode("Gb").build();
    ResponseEntity<WeatherApiResponse> entity = apiClient.getWeatherDetails(request);
    commonAssertion(entity);
  }

  @Test
  public void shouldThrowExceptionWhenCityNameInvalid() {
    WeatherRequest request = WeatherRequest.builder().cityName("anyString").build();
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> {
              apiClient.getWeatherDetails(request);
            });
    Assertions.assertThat(exception.getMessage()).contains("city not found");
  }

  @Test
  public void shouldThrowExceptionWhenCityNameAndCountryAreInvalid() {
    WeatherRequest request =
        WeatherRequest.builder().cityName("anyString").countryCode("anyString").build();
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> {
              apiClient.getWeatherDetails(request);
            });
    Assertions.assertThat(exception.getMessage()).contains("city not found");
  }

  @Test
  public void shouldThrowExceptionWhenCityNameIsEmpty() {
    WeatherRequest request = WeatherRequest.builder().cityName("  ").build();
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> {
              apiClient.getWeatherDetails(request);
            });
    Assertions.assertThat(exception.getMessage()).contains("city not found");
  }

  private void commonAssertion(ResponseEntity<WeatherApiResponse> entity) {
    Assertions.assertThat(entity).isNotNull();
    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(entity.getBody()).isNotNull();
    Assertions.assertThat(entity.getBody().getName()).isEqualTo("London");
    Assertions.assertThat(entity.getBody().getSys().getCountry()).isEqualTo("GB");
    Assertions.assertThat(entity.getBody().getWeather()).isNotEmpty();
    Assertions.assertThat(entity.getBody().getWeather()).isNotEmpty();
    Assertions.assertThat(entity.getBody().getWeather().size()).isEqualTo(1);
  }
}
