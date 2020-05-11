package com.weather.weatherservice.controller;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.service.WeatherHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Weather Controller")
public class WeatherController {

  private final WeatherHandler weatherHandler;

  @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Weather Service to get current weather")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Current weather returned")})
  public ResponseEntity<CurrentWeatherResponse> getCurrentWeather(@RequestParam String location) {
    return new ResponseEntity<>(
        weatherHandler.getCurrentWeatherByLocation(location), HttpStatus.OK);
  }

  @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Weather Service to get historic weather")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Historic weather returned")})
  public ResponseEntity<HistoricWeatherResponse> getHistoricWeather(@RequestParam String location) {
    return new ResponseEntity<>(
        weatherHandler.getHistoricWeatherByLocation(location), HttpStatus.OK);
  }
}
