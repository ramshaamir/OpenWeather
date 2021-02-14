package com.example.springbootdemo.Configurations;

import com.example.springbootdemo.Model.WeatherUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class WeatherConfigurations
{
  @Value("${weather.url}")
  private String url;

  @Value("${weather.apikey}")
  private String apikey;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

    PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
    c.setIgnoreUnresolvablePlaceholders(true);
    return c;
  }

  @Bean
  public WeatherUrl weatherUrl() {

    WeatherUrl weatherUrl = new WeatherUrl();
    weatherUrl.setUrl(url);
    weatherUrl.setApiKey(apikey);
    return weatherUrl;
  }
}
