package com.weather.weatherservice.repository;

import com.weather.weatherservice.entity.WeatherResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherResponseRepository extends JpaRepository<WeatherResponse, Long> {

  List<WeatherResponse> findByCityName(String cityName);

  List<WeatherResponse> findByCityNameAndCountryCode(String cityName, String countryCode);
}
