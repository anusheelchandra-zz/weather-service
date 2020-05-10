package com.weather.weatherservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class WeatherRestTemplateCustomizer implements RestTemplateCustomizer {

  private final ObjectMapper objectMapper;

  @Override
  public void customize(RestTemplate restTemplate) {
    restTemplate
        .getMessageConverters()
        .removeIf(
            httpMessageConverter ->
                httpMessageConverter.getClass() == MappingJackson2HttpMessageConverter.class);
    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter(objectMapper));
  }

  private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
      ObjectMapper objectMapper) {
    var messageConverter = new MappingJackson2HttpMessageConverter();
    messageConverter.setPrettyPrint(false);
    messageConverter.setObjectMapper(objectMapper);
    return messageConverter;
  }
}
