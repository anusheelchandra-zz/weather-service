package com.weather.weatherservice.service;

import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.domain.WeatherRequest;
import com.weather.weatherservice.entity.WeatherResponse;
import com.weather.weatherservice.exception.WeatherServiceException;
import com.weather.weatherservice.mapper.ToHistoricWeatherResponseMapper;
import com.weather.weatherservice.repository.WeatherResponseRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricWeatherService {

  private final WeatherResponseRepository repository;

  /*
  Here am assuming that for historic data , we need to return all records and
  average temperature and pressure calculated on the base of last 5 records.

  NOTE: The open weather api kind of doesnt work properly with countryCode.
  One get same result for Berlin,de and Berlin,anyString
  */
  public Optional<HistoricWeatherResponse> getHistoricWeather(WeatherRequest request) {
    try {
      return ((request.getCountryCode() == null) || request.getCountryCode().isEmpty())
          ? getHistoricWeatherByCity(request)
          : getHistoricWeatherByCityAndCountry(request);
    } catch (Exception ex) {
      // Need proper exception handling
      throw new WeatherServiceException(ex.getMessage(), ex);
    }
  }

  private Optional<HistoricWeatherResponse> getHistoricWeatherByCity(WeatherRequest request) {
    List<WeatherResponse> weatherResponsesByCity = repository.findByCityName(request.getCityName());

    return weatherResponsesByCity.isEmpty()
        ? Optional.empty()
        : getHistoricWeatherResponse(weatherResponsesByCity);
  }

  private Optional<HistoricWeatherResponse> getHistoricWeatherByCityAndCountry(
      WeatherRequest request) {
    List<WeatherResponse> weatherResponsesByCityAndCountry =
        repository.findByCityNameAndCountryCode(request.getCityName(), request.getCountryCode());

    return weatherResponsesByCityAndCountry.isEmpty()
        ? Optional.empty()
        : getHistoricWeatherResponse(weatherResponsesByCityAndCountry);
  }

  private Optional<HistoricWeatherResponse> getHistoricWeatherResponse(
      List<WeatherResponse> weatherResponseList) {
    return Optional.ofNullable(
        ToHistoricWeatherResponseMapper.toHistoricWeatherResponse(weatherResponseList));
  }
}
