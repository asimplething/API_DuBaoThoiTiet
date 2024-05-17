package com.example.weather.Services;

public class WeatherApiResponse {
	private Location location;
    private Current current;

    public static class Location {
        private String name;
        private String country;
        private String tz_id;
       

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTz_id() {
            return tz_id;
        }

        public void setTz_id(String tz_id) {
            this.tz_id = tz_id;
        }

	

       
    }

    public static class Current {
        private double temp_c;
        private String last_updated;
        // Getters and Setters
        public double getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(double temp_c) {
            this.temp_c = temp_c;
        }

		public String getLast_updated() {
			return last_updated;
		}

		public void setLast_updated(String last_updated) {
			this.last_updated = last_updated;
		}
        
    }

    // Getters and Setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
