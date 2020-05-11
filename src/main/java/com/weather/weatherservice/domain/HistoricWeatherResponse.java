package com.weather.weatherservice.domain;

import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(
      notes = "Average temperature based on last 5 records for a location expressed in Kelvin")
  private Float averageTemperature;

  @ApiModelProperty(
      notes = "Average pressure based on last 5 records for a location expressed in Kelvin")
  private Float averagePressure;

  @ApiModelProperty(notes = "Hitoric weather records for a location")
  private List<CurrentWeatherResponse> history;
}
