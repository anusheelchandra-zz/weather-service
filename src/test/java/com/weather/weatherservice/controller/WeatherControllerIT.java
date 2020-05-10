package com.weather.weatherservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.service.WeatherHandler;
import java.io.UnsupportedEncodingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

/* Ignoring this Integration Test from test suite because it depends on third part api, but can be run manually or by
removing @Disable */
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIT {

  private static final String CURRENT_WEATHER_URI = "/current";
  private static final String HISTORIC_WEATHER_URI = "/history";

  @Autowired private MockMvc mockMvc;
  @Autowired private WeatherHandler weatherHandler;
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void shouldGetCurrentWeatherByCity() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("location", "London"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    Assertions.assertThat(mvcResult).isNotNull();
    Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

    currentWeatherAssertions(mvcResult);
  }

  @Test
  public void shouldGetCurrentWeatherByCityAndCountry() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("location", "London,GB"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    Assertions.assertThat(mvcResult).isNotNull();
    Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

    currentWeatherAssertions(mvcResult);
  }

  @Test
  public void shouldGetHistoricWeatherByCity() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(HISTORIC_WEATHER_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("location", "London"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    Assertions.assertThat(mvcResult).isNotNull();
    Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

    historicWeatherAssertions(mvcResult);
  }

  @Test
  public void shouldGetHistoricWeatherByCityAndCountry() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(HISTORIC_WEATHER_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("location", "London,GB"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    Assertions.assertThat(mvcResult).isNotNull();
    Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

    historicWeatherAssertions(mvcResult);
  }

  @Test
  public void shouldThrowExceptionWhileGetCurrentWeatherWhenCityIsNull() throws Exception {
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            NestedServletException.class,
            () -> {
              mockMvc.perform(
                  MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .param("location", ""));
            });
    Assertions.assertThat(exception.getMessage())
        .contains("Required String parameter 'location' is not valid");
  }

  @Test
  public void shouldThrowExceptionWhileGetCurrentWeatherWhenCityIsInvalid() throws Exception {
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            NestedServletException.class,
            () -> {
              mockMvc.perform(
                  MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .param("location", "anyCity"));
            });
    Assertions.assertThat(exception.getMessage()).contains("city not found");
  }

  @Test
  public void shouldThrowExceptionWhileGetHistoricWeatherWhenCityIsNull() throws Exception {
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            NestedServletException.class,
            () -> {
              mockMvc.perform(
                  MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .param("location", ""));
            });
    Assertions.assertThat(exception.getMessage())
        .contains("Required String parameter 'location' is not valid");
  }

  @Test
  public void shouldThrowExceptionWhileGetHistoricWeatherWhenCityIsInvalid() throws Exception {
    Exception exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            NestedServletException.class,
            () -> {
              mockMvc.perform(
                  MockMvcRequestBuilders.get(CURRENT_WEATHER_URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .param("location", "anyCity"));
            });
    Assertions.assertThat(exception.getMessage()).contains("city not found");
  }

  private void currentWeatherAssertions(MvcResult mvcResult)
      throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
    CurrentWeatherResponse response =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), CurrentWeatherResponse.class);
    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getTemperature()).isNotNull();
    Assertions.assertThat(response.getPressure()).isNotNull();
    Assertions.assertThat(response.getUmbrella()).isNotNull();
  }

  private void historicWeatherAssertions(MvcResult mvcResult)
      throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
    HistoricWeatherResponse response =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), HistoricWeatherResponse.class);
    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getAveragePressure()).isNotNull();
    Assertions.assertThat(response.getAverageTemperature()).isNotNull();
    Assertions.assertThat(response.getHistory()).isNotEmpty();
    response
        .getHistory()
        .forEach(
            currentWeatherResponse -> Assertions.assertThat(currentWeatherResponse).isNotNull());
    response
        .getHistory()
        .forEach(
            currentWeatherResponse ->
                Assertions.assertThat(currentWeatherResponse.getPressure()).isNotNull());
    response
        .getHistory()
        .forEach(
            currentWeatherResponse ->
                Assertions.assertThat(currentWeatherResponse.getTemperature()).isNotNull());
    response
        .getHistory()
        .forEach(
            currentWeatherResponse ->
                Assertions.assertThat(currentWeatherResponse.getUmbrella()).isNotNull());
  }
}
