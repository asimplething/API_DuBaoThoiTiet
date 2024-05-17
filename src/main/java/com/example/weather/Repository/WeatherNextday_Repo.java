package com.example.weather.Repository;


import org.springframework.stereotype.Repository;

import com.example.weather.entity.Weather_Nextday;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface WeatherNextday_Repo extends JpaRepository<Weather_Nextday, Long> {
    @Query("SELECT w FROM Weather_Nextday w WHERE w.city = :city")
    List<Weather_Nextday> findDailyWeatherForecast(@Param("city") String city);
}

