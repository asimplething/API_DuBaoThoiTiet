package com.example.weather.Repository;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.weather.entity.Recent_Weather;
import com.example.weather.entity.Weather_Nextday;

import jakarta.transaction.Transactional;
@Repository
public interface RecentWeather_Repo extends JpaRepository<Recent_Weather, Long> {
	List<Recent_Weather> findByCityAndDate(String city, LocalDate date);
	@Transactional
    @Modifying
    @Query("DELETE FROM Weather_Hourly w WHERE w.city = :city AND w.date = :date")
    void deleteAllByCityAndDate(@Param("city") String city, @Param("date") LocalDate date);
	  List<Recent_Weather> findByCityAndDateAfter(String city, LocalDate date);
}
