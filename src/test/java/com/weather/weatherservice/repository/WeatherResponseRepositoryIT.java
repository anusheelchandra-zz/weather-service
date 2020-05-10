package com.weather.weatherservice.repository;

import com.weather.weatherservice.entity.WeatherCondition;
import com.weather.weatherservice.entity.WeatherResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherResponseRepositoryIT {

  @Autowired private WeatherResponseRepository repository;

  @BeforeEach
  public void setup() {
    WeatherResponse savedWeatherResponse = repository.save(buildWeatherResponse());
  }

  @Test
  public void shouldFindByCityName() {
    List<WeatherResponse> weatherResponses = repository.findByCityName("london");
    commonAssertions(weatherResponses);
  }

  @Test
  public void shouldNotFindByCityName() {
    List<WeatherResponse> weatherResponses = repository.findByCityName("berlin");
    Assertions.assertThat(weatherResponses).isEmpty();
  }

  @Test
  public void shouldFindByCityNameAndCountryCode() {
    List<WeatherResponse> weatherResponses =
        repository.findByCityNameAndCountryCode("london", "gb");
    commonAssertions(weatherResponses);
  }

  @Test
  public void shouldNotFindByCityNameAndCountryCode() {
    List<WeatherResponse> weatherResponses =
        repository.findByCityNameAndCountryCode("berlin", "de");
    Assertions.assertThat(weatherResponses).isEmpty();
  }

  @AfterEach
  public void tearDown() {
    repository.deleteAll();
  }

  private void commonAssertions(List<WeatherResponse> weatherResponses) {
    Assertions.assertThat(weatherResponses).isNotEmpty();
    Assertions.assertThat(weatherResponses.size()).isEqualTo(1);
    Assertions.assertThat(weatherResponses.get(0).getCityName()).isEqualTo("london");
    Assertions.assertThat(weatherResponses.get(0).getCountryCode()).isEqualTo("gb");
    Assertions.assertThat(weatherResponses.get(0).getTemperature())
        .isEqualTo(Float.valueOf(1001.0f));
    Assertions.assertThat(weatherResponses.get(0).getPressure()).isEqualTo(Float.valueOf(1201.0f));
    Assertions.assertThat(weatherResponses.get(0).getCondition()).isNotEmpty();
    Assertions.assertThat(weatherResponses.get(0).getCondition().size()).isEqualTo(1);
    Assertions.assertThat(weatherResponses.get(0).getCondition().iterator().next().getDescription())
        .isEqualTo("Rain");
  }

  private WeatherResponse buildWeatherResponse() {
    return WeatherResponse.builder()
        .cityName("london")
        .countryCode("gb")
        .temperature(1001.0f)
        .pressure(1201.0f)
        .created(LocalDateTime.now())
        .condition(Collections.singleton(WeatherCondition.builder().description("Rain").build()))
        .build();
  }
}
