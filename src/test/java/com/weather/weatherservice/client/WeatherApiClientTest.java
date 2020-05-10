package com.weather.weatherservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import java.io.IOException;
import java.io.InputStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherApiClientTest {

  @Mock private RestTemplate testTemplate;
  private ObjectMapper objectMapper;

  private WeatherApiClient apiClient;

  @BeforeEach
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    apiClient = new WeatherApiClient(testTemplate);
    objectMapper = new ObjectMapper();

    WeatherApiResponse weatherApiResponse = getWeatherResponse();
    Mockito.when(
            testTemplate.getForEntity(
                "http://api.openweathermap.org/data/2.5/weather?q=london&APPID=0f960ddcf4b96b556ca9a39bdc473e89",
                WeatherApiResponse.class))
        .thenReturn(new ResponseEntity<>(weatherApiResponse, HttpStatus.OK));

    Mockito.when(
            testTemplate.getForEntity(
                "http://api.openweathermap.org/data/2.5/weather?q=london,gb&APPID=0f960ddcf4b96b556ca9a39bdc473e89",
                WeatherApiResponse.class))
        .thenReturn(new ResponseEntity<>(weatherApiResponse, HttpStatus.OK));
  }

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

  private WeatherApiResponse getWeatherResponse() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("/weatherApiResponse.json");
    return objectMapper.readValue(inputStream, WeatherApiResponse.class);
  }
}
