package com.weather.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

  private List<WeatherDetails> weather;
  private String base;
  private Weather main;
  private String visibility;
  private long dt;
  private String name;
  private Country sys;
  private long id;
}
