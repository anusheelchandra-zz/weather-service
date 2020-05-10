package com.weather.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  private Float temperature;
  private Float pressure;
  private Boolean umbrella;

  @JsonIgnore private LocalDateTime created;
}
