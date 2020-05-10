package com.weather.weatherservice.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricWeatherResponse {

  private Float averageTemperature;
  private Float averagePressure;
  private List<CurrentWeatherResponse> history;
}
