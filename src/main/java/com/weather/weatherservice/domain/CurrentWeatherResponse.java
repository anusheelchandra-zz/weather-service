package com.weather.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeatherResponse {

  @ApiModelProperty(notes = "Temperature of location expressed in Kelvin")
  private Float temperature;

  @ApiModelProperty(notes = "Pressure of location expressed in Kelvin")
  private Float pressure;

  @ApiModelProperty(notes = "Boolean expressing if umbrella is required to go out")
  private Boolean umbrella;

  @JsonIgnore private LocalDateTime created;
}
