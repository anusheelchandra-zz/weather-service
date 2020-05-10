package com.weather.weatherservice.service;

import com.weather.weatherservice.client.WeatherApiClient;
import com.weather.weatherservice.domain.WeatherApiResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.exception.WeatherApiException;
import com.weather.weatherservice.mapper.ToWeatherResponseMapper;
import com.weather.weatherservice.repository.WeatherResponseRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

  private final WeatherApiClient weatherApiClient;
  private final WeatherResponseRepository repository;

  public Optional<WeatherResponse> getCurrentWeather(WeatherRequest request) {
    try {
      ResponseEntity<WeatherApiResponse> responseEntity =
          weatherApiClient.getWeatherDetails(request);
      if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
        WeatherResponse savedWeatherResponse =
            repository.save(ToWeatherResponseMapper.toWeatherResponse(responseEntity.getBody()));
        return Optional.of(savedWeatherResponse);
      }
      //
    } catch (Exception ex) {
      // Need proper exception handling
      throw new WeatherApiException(ex.getMessage(), ex);
    }
    return Optional.empty();
  }
}
