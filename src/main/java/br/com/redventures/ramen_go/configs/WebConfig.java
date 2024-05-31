package br.com.redventures.ramen_go.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final ObjectMapper objectMapper;

  public WebConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(plainTextToJsonMessageConverter());
  }

  @Bean
  HttpMessageConverter<Object> plainTextToJsonMessageConverter() {
    return new PlainTextToJsonMessageConverter(objectMapper);
  }

}
