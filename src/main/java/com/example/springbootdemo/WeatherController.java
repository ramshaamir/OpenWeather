package com.example.springbootdemo;

import com.example.springbootdemo.Model.FormAttributes;
import com.example.springbootdemo.Model.Weather;
import com.example.springbootdemo.Model.WeatherUrl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.alicp.jetcache.anno.Cached;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WeatherController
{
  @Autowired
  RestTemplate restTemp;

  @Autowired
  private WeatherUrl weatherData;

  @RequestMapping(value = "/weather", method = RequestMethod.GET)
  public String CityForm(Model model)
  {
    model.addAttribute("city", new FormAttributes());
    return "formData";
  }

  @Cached(expire = 120, timeUnit = TimeUnit.MINUTES)
  @RequestMapping(value = "/weatherWithCity", method = RequestMethod.POST)
  public String getWeather(Model model, @ModelAttribute FormAttributes city)
          throws JsonParseException, JsonMappingException, IOException
  {
    UriComponents uriComponents = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(weatherData.getUrl())
            .path("")
            .query("q={keyword}&appkey={appid}")
            .buildAndExpand(city.getCity(), weatherData.getApiKey());

    String uri = uriComponents.toUriString();

    ResponseEntity<String> resp = restTemp.exchange(uri, HttpMethod.GET, null, String.class);
    ObjectMapper mapper = new ObjectMapper();
    Weather weather = mapper.readValue(resp.getBody(), Weather.class);
    model.addAttribute("weatherData", weather);

    return "weatherDetails";
  }

  @Cached(expire = 120, timeUnit = TimeUnit.MINUTES)
  @RequestMapping(value = "/weatherWithLatLon", method = RequestMethod.POST)
  public String getWeatherLAT(Model model, @ModelAttribute Weather weatherModel)
          throws JsonParseException, JsonMappingException, IOException
  {
    UriComponents uriComponents = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(weatherData.getUrl())
            .path("")
            .query("lat={lat}&lon={lon}&appkey={appid}")
            .buildAndExpand(weatherModel.getLat(),weatherModel.getLon(), weatherData.getApiKey());

    String uri = uriComponents.toUriString();

    ResponseEntity<String> resp = restTemp.exchange(uri, HttpMethod.GET, null, String.class);
    ObjectMapper mapper = new ObjectMapper();
    Weather weather = mapper.readValue(resp.getBody(), Weather.class);
    model.addAttribute("weatherData", weather);

    return "weatherDetails";
  }

}
