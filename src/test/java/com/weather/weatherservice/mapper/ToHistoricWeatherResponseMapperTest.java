package com.weather.weatherservice.mapper;

import com.weather.weatherservice.domain.CurrentWeatherResponse;
import com.weather.weatherservice.domain.HistoricWeatherResponse;
import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToHistoricWeatherResponseMapperTest {

  @Test
  public void shouldMapCurrentWeatherResponseToHistoricWeatherResponse() {
    HistoricWeatherResponse historicWeather =
        ToHistoricWeatherResponseMapper.toHistoricWeatherResponse(getCurrentWeatherResponse());
    Assertions.assertThat(historicWeather.getAveragePressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(historicWeather.getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeather.getHistory().size()).isEqualTo(1);
    commonAssertion(historicWeather.getHistory().get(0));
  }

  @Test
  public void shouldMapWeatherResponsesToHistoricWeatherResponse() {
    HistoricWeatherResponse historicWeatherResponse =
        ToHistoricWeatherResponseMapper.toHistoricWeatherResponse(getWeatherResponseList());
    Assertions.assertThat(historicWeatherResponse).isNotNull();
    Assertions.assertThat(historicWeatherResponse.getAverageTemperature())
        .isEqualTo(Float.valueOf(340.0f));
    Assertions.assertThat(historicWeatherResponse.getAveragePressure())
        .isEqualTo(Float.valueOf(152.0f));
    Assertions.assertThat(historicWeatherResponse.getHistory()).isNotEmpty();
    Assertions.assertThat(historicWeatherResponse.getHistory().size()).isEqualTo(6);
  }

  private void commonAssertion(CurrentWeatherResponse currentWeather) {
    Assertions.assertThat(currentWeather.getUmbrella()).isTrue();
    Assertions.assertThat(currentWeather.getPressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(currentWeather.getTemperature()).isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(currentWeather.getCreated()).isInstanceOf(LocalDateTime.class);
  }

  private List<WeatherResponse> getWeatherResponseList() {
    return List.of(
        getWeatherResponse(100f, 50f, 1),
        getWeatherResponse(200f, 10f, 2),
        getWeatherResponse(300f, 150f, 3),
        getWeatherResponse(400f, 200f, 4),
        getWeatherResponse(400f, 200f, 5),
        getWeatherResponse(400f, 200f, 6));
  }

  private WeatherResponse getWeatherResponse(float temp, float pressure, long min) {
    return WeatherResponse.builder()
        .cityName("london")
        .countryCode("gb")
        .temperature(temp)
        .pressure(pressure)
        .created(LocalDateTime.now().plusMinutes(min))
        .condition(Collections.singleton(WeatherCondition.builder().description("Rain").build()))
        .build();
  }

  private CurrentWeatherResponse getCurrentWeatherResponse() {
    return CurrentWeatherResponse.builder()
        .umbrella(true)
        .temperature(1001.0f)
        .pressure(1201.0f)
        .created(LocalDateTime.now())
        .build();
  }
}
