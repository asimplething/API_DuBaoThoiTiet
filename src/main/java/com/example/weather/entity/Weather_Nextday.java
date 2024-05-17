package com.example.weather.entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Weather_Nextday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayOfWeek;
    private String icon;
    private int chanceOfRain;
    private double minTemperature;
    private double maxTemperature;
    private LocalDate date;
    private String city;  // Add this line

    // Constructors, getters, and setters

    public Weather_Nextday() {
    }

    public Weather_Nextday(String dayOfWeek, String icon, int chanceOfRain, double minTemperature, double maxTemperature, LocalDate date, String city) {
        this.dayOfWeek = dayOfWeek;
        this.icon = icon;
        this.chanceOfRain = chanceOfRain;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.date = date;
        this.city = city;  // Add this line
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
