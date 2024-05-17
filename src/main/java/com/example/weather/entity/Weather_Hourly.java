	package com.example.weather.entity;
	
	import java.time.LocalDate;
	
	import jakarta.annotation.Generated;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	
	@Entity
	public class Weather_Hourly {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String time;
	    private String temperature;
	    private String condition;
	    private String icon;
	    private String city; // Thêm trường city
	    private LocalDate date;
	    // Constructors, getters, and setters
	
	    public Weather_Hourly() {}
	
	    public Weather_Hourly(String time, String temperature, String condition, String icon, String City,LocalDate date) {
	        this.time = time;
	        this.temperature = temperature;
	        this.condition = condition;
	        this.icon = icon;
	        this.city = City;
	        this.date = date;
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
	
	    // Getters and setters
	}
