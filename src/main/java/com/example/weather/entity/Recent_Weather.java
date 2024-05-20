	package com.example.weather.entity;
	
	import java.time.LocalDate;
	
	import jakarta.annotation.Generated;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	
	@Entity
	public class Recent_Weather {
		 @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;
		    private String time;
		    private String temperature;
		    private String condition;
		    private String icon;
		    private String city; // Thêm trường city
		    private LocalDate date;
		    private int humidity;
		    private double wind_speed;
		    // Constructors, getters, and setters
		
		    public  Recent_Weather() {}
		
		  
		
			public Recent_Weather( String time, String temperature, String condition, String icon, String city,
					LocalDate date, int humidity, double wind_speed) {
				this.id = id;
				this.time = time;
				this.temperature = temperature;
				this.condition = condition;
				this.icon = icon;
				this.city = city;
				this.date = date;
				this.humidity = humidity;
				this.wind_speed = wind_speed;
			}
	
	
	
			public int getHumidity() {
				return humidity;
			}
	
	
	
			public void setHumidity(int humidity) {
				this.humidity = humidity;
			}
	
	
	
			public double getWind_speed() {
				return wind_speed;
			}
	
	
	
			public void setWind_speed(float wind_speed) {
				this.wind_speed = wind_speed;
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
		
			public String getIcon() {
				return icon;
			}
		
			public void setIcon(String icon) {
				this.icon = icon;
			}
		
			public Long getId() {
				return id;
			}
		
			public void setId(Long id) {
				this.id = id;
			}
		
			public String getTime() {
				return time;
			}
		
			public void setTime(String time) {
				this.time = time;
			}
		
			public String getTemperature() {
				return temperature;
			}
		
			public void setTemperature(String temperature) {
				this.temperature = temperature;
			}
		
			public String getCondition() {
				return condition;
			}
		
			public void setCondition(String condition) {
				this.condition = condition;
			}
		
	}
