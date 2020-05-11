package com.weather.weatherservice.service;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.mapper.ToCurrentWeatherResponseMapper;
import com.weather.weatherservice.mapper.ToHistoricWeatherResponseMapper;
import com.weather.weatherservice.util.WeatherRequestFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherHandler {

  private final WeatherRequestFactory requestFactory;
  private final WeatherApiService weatherApiService;
  private final HistoricWeatherService historicWeatherService;

  public CurrentWeatherResponse getCurrentWeatherByLocation(String location) {
    return getCurrentWeather(location);
  }

  public HistoricWeatherResponse getHistoricWeatherByLocation(String location) {
    return getHistoricWeather(location);
  }

  private CurrentWeatherResponse getCurrentWeather(String location) {
    WeatherRequest weatherRequest = requestFactory.createWeatherRequest(location);
    validateRequest();
    Optional<WeatherResponse> weatherResponse = weatherApiService.getCurrentWeather(weatherRequest);
    return weatherResponse
        .map(ToCurrentWeatherResponseMapper::toCurrentWeatherResponse)
        .orElse(CurrentWeatherResponse.builder().build());
  }

  private HistoricWeatherResponse getHistoricWeather(String location) {
    WeatherRequest weatherRequest = requestFactory.createWeatherRequest(location);
    validateRequest();
    Optional<HistoricWeatherResponse> historicWeatherResponse =
        historicWeatherService.getHistoricWeather(weatherRequest);
    return historicWeatherResponse.orElseGet(
        () ->
            ToHistoricWeatherResponseMapper.toHistoricWeatherResponse(getCurrentWeather(location)));
  }

  private void validateRequest() {
    //TODO validate the city name and country code
  }
}
