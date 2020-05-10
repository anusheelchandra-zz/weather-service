package com.weather.weatherservice.client;

import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.util.WeatherApiUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherApiClient {

  private final RestTemplate restTemplate;

  public ResponseEntity<WeatherApiResponse> getWeatherDetails(WeatherRequest request) {
    return restTemplate.getForEntity(
        WeatherApiUrlUtil.getWeatherApiURI(request), WeatherApiResponse.class);
  }
}
