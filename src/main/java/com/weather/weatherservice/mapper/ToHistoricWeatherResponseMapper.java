package com.weather.weatherservice.mapper;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.entity.WeatherResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ToHistoricWeatherResponseMapper {

  private static final int NUMBER_OF_RECORDS_TO_CALCULATE_AVG = 5;

  public HistoricWeatherResponse toHistoricWeatherResponse(List<WeatherResponse> responses) {
    List<CurrentWeatherResponse> weatherResponseList =
        responses.stream()
            .map(ToCurrentWeatherResponseMapper::toCurrentWeatherResponse)
            .collect(Collectors.toList());

    List<CurrentWeatherResponse> averageCalculationList =
        weatherResponseList.stream()
            .sorted(Comparator.comparing(CurrentWeatherResponse::getCreated).reversed())
            .limit(NUMBER_OF_RECORDS_TO_CALCULATE_AVG)
            .collect(Collectors.toList());

    return HistoricWeatherResponse.builder()
        .averageTemperature(calculateAverageTemperature(averageCalculationList).floatValue())
        .averagePressure(calculateAveragePressure(averageCalculationList).floatValue())
        .history(weatherResponseList)
        .build();
  }

  public HistoricWeatherResponse toHistoricWeatherResponse(CurrentWeatherResponse response) {
    return HistoricWeatherResponse.builder()
        .averageTemperature(response.getTemperature())
        .averagePressure(response.getPressure())
        .history(List.of(response))
        .build();
  }

  private Double calculateAverageTemperature(List<CurrentWeatherResponse> averageCalculationList) {
    return averageCalculationList.stream()
        .mapToDouble(CurrentWeatherResponse::getTemperature)
        .average()
        .getAsDouble();
  }

  private Double calculateAveragePressure(List<CurrentWeatherResponse> averageCalculationList) {
    return averageCalculationList.stream()
        .mapToDouble(CurrentWeatherResponse::getPressure)
        .average()
        .getAsDouble();
  }
}
