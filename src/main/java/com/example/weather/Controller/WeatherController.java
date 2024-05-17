package com.example.weather.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.weather.Services.WeatherService;
import com.example.weather.entity.Weather_Hourly;
import com.example.weather.entity.Weather_Nextday;

import java.util.List;
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	 @Autowired
	    private WeatherService weatherService;

	    @GetMapping("/hourly")
	    public List<Weather_Hourly> getHourlyWeather(@RequestParam String city) {
	    
	        return weatherService.getHourlyWeather(city);
	    }
	 // Thêm vào WeatherController
	    @GetMapping("/daily")
	    public List<Weather_Nextday> getDailyWeatherForecast(@RequestParam String city) {
	    
	       //weatherService.updateDailyWeatherForecast(city); // Cập nhật dữ liệu thời tiết của 7 ngày tiếp theo
	        // Trả về dữ liệu thời tiết của 7 ngày tiếp theo
	        return weatherService.getDailyWeatherForecast(city);
	    }

	 
}
