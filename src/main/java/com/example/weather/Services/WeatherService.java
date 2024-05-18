	package com.example.weather.Services;
	
	import java.time.LocalDate;
	import java.time.LocalDateTime;
	import java.time.ZonedDateTime;
	import java.time.format.DateTimeFormatter;
	import java.util.Map;
	import java.util.ArrayList;
	import java.util.List;
	
	import com.example.weather.Repository.WeatherNextday_Repo;
	import com.example.weather.Repository.Weather_Repo;
	import com.example.weather.entity.*;
	
	import jakarta.annotation.PostConstruct;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.scheduling.annotation.Scheduled;
	import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestTemplate;
	
	import java.util.Random;
import java.util.stream.Collectors;
import java.util.HashMap;
	@Service
	public class WeatherService {
		@Autowired
	    private Weather_Repo weatherRepository;
		@Autowired
		private WeatherNextday_Repo weatherNextday_Repository;
	    private Random random = new Random();
	    private final String API_KEY = "4c57a8be9b2b4def8d833930240905";
	    private final String API_URL = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q={city}";
	
	    private static final Map<String, String> DAY_WEATHER_CONDITIONS = new HashMap<>();
	
	    private static final Map<String, String> NIGHT_WEATHER_CONDITIONS = new HashMap<>();
	
	    static {
	        
	        // Thêm các cặp key-value (key là tình trạng thời tiết, value là những cái ảnh t tải từ các trang dự báo thời tiết về và upload lên internet.
	        DAY_WEATHER_CONDITIONS.put("Sunny", "https://i.ibb.co/d4XzGBT/Sunny.png");
	        DAY_WEATHER_CONDITIONS.put("Mostly Sunny", "https://i.ibb.co/WVDttyJ/Mostly-Sunny.png");
	        DAY_WEATHER_CONDITIONS.put("Partly Sunny", "https://i.ibb.co/k47KvTp/Intermittent-Clouds.png");
	        DAY_WEATHER_CONDITIONS.put("Intermittent Clouds", "https://i.ibb.co/k47KvTp/Intermittent-Clouds.png"); //4
	        DAY_WEATHER_CONDITIONS.put("Hazy Sunshine", "https://i.ibb.co/vXY7WLb/Hazy-Sunshine.png"); //5
	        DAY_WEATHER_CONDITIONS.put("Mostly Cloudy", "https://i.ibb.co/ZSbxxCB/Mostly-Cloudy.png"); //6
	        DAY_WEATHER_CONDITIONS.put("Cloudy", "https://i.ibb.co/MkDpxz4/Cloudy.png"); //7
	        DAY_WEATHER_CONDITIONS.put("Overcast", "https://i.ibb.co/hsMSz0M/Overcast.png"); //8
	        DAY_WEATHER_CONDITIONS.put("Fog", "https://i.ibb.co/hsMSz0M/Overcast.png"); //9
	        DAY_WEATHER_CONDITIONS.put("Showers", "https://i.ibb.co/BB22b7b/Showers.png"); //10
	        DAY_WEATHER_CONDITIONS.put("Mostly Cloudy Showers", "https://i.ibb.co/MpkmC9J/Mostly-Cloudy-Showers.png"); //11
	        DAY_WEATHER_CONDITIONS.put("Partly Sunny with Showers", "https://i.ibb.co/k2rqNS4/Partly-Sunny-w-Showers.png"); //12
	        DAY_WEATHER_CONDITIONS.put("T-Storms", "https://i.ibb.co/2FdtHYG/T-Storms.png"); //13
	        DAY_WEATHER_CONDITIONS.put("Mostly Cloudy with T-Storms", "https://i.ibb.co/f81RtH1/Mostly-Cloudy-w-T-Storms.png"); //14
	        DAY_WEATHER_CONDITIONS.put("Rain", "https://i.ibb.co/st7Wxzf/Rain.png"); //15
	        DAY_WEATHER_CONDITIONS.put("Flurries", "https://i.ibb.co/BBBZ4WS/Flurries.png"); //16
	        DAY_WEATHER_CONDITIONS.put("Snow", "https://i.ibb.co/hMGZNKZ/Snow.png"); //17
	        DAY_WEATHER_CONDITIONS.put("Ice", "https://i.ibb.co/TgK15jt/Ice.png"); //18
	        DAY_WEATHER_CONDITIONS.put("Rain and Snow", "https://ibb.co/WP8nNCH"); //19
	        
	        NIGHT_WEATHER_CONDITIONS.put("Clear", "https://i.ibb.co/SV6R2hN/Clear.png"); //20
	        NIGHT_WEATHER_CONDITIONS.put("Mostly Clear", "https://i.ibb.co/SV6R2hN/Clear.png"); //21
	        NIGHT_WEATHER_CONDITIONS.put("Partly Cloudy", "https://i.ibb.co/G3yBCm1/Partly-Cloudy.png"); //22
	        NIGHT_WEATHER_CONDITIONS.put("Mostly Cloudy", "https://i.ibb.co/VtR5bKx/Mostly-Cloudy.png"); //23
	        NIGHT_WEATHER_CONDITIONS.put("Partly Cloudy with Showers", "https://i.ibb.co/TbpNJYS/Partly-Cloudy-w-Showers.png"); //24
	        NIGHT_WEATHER_CONDITIONS.put("Mostly Cloudy with Showers", "https://i.ibb.co/sjGh47G/Mostly-Cloudy-w-Showers.png"); //25
	        NIGHT_WEATHER_CONDITIONS.put("Partly Cloudy w T-Storms", "https://i.ibb.co/tBHqLYm/Partly-Cloudy-w-T-Storms.png"); //26
	        NIGHT_WEATHER_CONDITIONS.put("Mostly Cloudy w Snow", "https://i.ibb.co/M9H2v2D/Mostly-Cloudy-w-Snow.png"); //27
	
	    }
	    @Scheduled(cron = "0 0 0 * * ?") // Reset vào 0:00 mỗi ngày
	    public void resetDailyWeather() {
	        weatherRepository.deleteAll();
	    }
	    @Scheduled(fixedRate = 3600000) // Cập nhật mỗi giờ
	    public void updateHourlyWeather(String city,WeatherApiResponse weatherData) {
	    	  if (weatherData == null) return;
	    	//Lấy thời gian hiện tại để tính toán thời tiết cho các giờ tiếp theo.
	        String currentTimeStr = weatherData.getCurrent().getLast_updated();
	        LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        int currentHour = currentTime.getHour();
	        LocalDate currentDate = currentTime.toLocalDate();
	        
	    	 

	      
	
	        
	        	
	        	 double currentTemperature = weatherData.getCurrent().getTemp_c();
	        	 List<Double> hourlyTemperatures = calculateHourlyTemperatures(currentTemperature, currentHour);
	            
	        	 for (int i = currentHour+1; i < 24; i++) {
	            	String temperature = String.format("%.1f°C", hourlyTemperatures.get(i - currentHour - 1));
	                String condition = getRandomWeatherCondition(currentTime.plusHours(i - currentHour),temperature);
	                String icon = condition != null ? DAY_WEATHER_CONDITIONS.getOrDefault(condition, NIGHT_WEATHER_CONDITIONS.get(condition)) : null;
	                
	                
	                String time = currentTime.plusHours(i - currentHour).format(DateTimeFormatter.ofPattern("HH:00"));
	                weatherRepository.save(new Weather_Hourly(time, temperature, condition, icon, city,currentDate));
	            }
	       
	    }
	 //Lấy nhiệt độ hiện tại từ API có sẵn trả về để tính toán ra các nhiệt độ trong ngày:
		/*
		 * Dựa trên 1 số dữ liệu phân tích được:
		  6h →10h nhiệt độ tăng đều lại
		  đến khoảng 11h → 15h nhiệt độ là cao nhất trong ngày
		 >15h thì nhiệt độ giảm đến 5h sáng hôm sau là giảm từ từ. 
		*/
	    private List<Double> calculateHourlyTemperatures(double currentTemperature, int currentHour) {
	        List<Double> hourlyTemperatures = new ArrayList<>(24);
	        //Ta có thể tính nhiệt độ tại thời điểm t dựa trên 1 số công thức toán học và biểu diễn đồ thị nhiệt độ rồi dùng tích phân
	        //ta tính được hàm theo quy luật như sau: T(t) = Ttb + A*(sin((2*pi/24)*(t-tmax))+ φ) - có thể chứng minh bằng tích phân dựa trên các đồ thị về thời tiết. Sau khi có Ttb ta có thể ước lượng random giá trị cho Tmax và Tmin. (Tmax > Ttb & Tmin < Ttb)
	        //Nếu thời gian từ 0->5 thì φ = 0. Các tgian còn lại thì φ = -49*pi/32.
	       double Temp_avg= 0;
	      if(currentHour>=0 && currentHour<=5)  Temp_avg = currentTemperature - Math.sin(((2*Math.PI)/24)*(currentHour-14))*4   ;		//7 là A chính là biên độ nhiệt ước tính được dựa trên quan sát tập dữ liệu và lấy ra giá trị trung bình.
	      else Temp_avg = currentTemperature - Math.sin(((2*Math.PI)*(currentHour-14)/24) - (49*Math.PI)/32)*4 ;
	        for (int hour = currentHour + 1; hour < 24; hour++) {
	            double temp_t;		//nhiệt độ tại thời điểm t:
	            double SIN = 0;
	            if(hour >=0 && hour <=5)
	            { SIN = Math.sin(((2*Math.PI)*(hour-14)/24));}
	            else SIN = Math.sin(((2*Math.PI)*(hour-14)/24) - (49*Math.PI)/32);
	            temp_t = Temp_avg + 4*(SIN);
	            hourlyTemperatures.add(temp_t);
	            
	        }
	        return hourlyTemperatures;
	    }
	    private String getRandomWeatherCondition(LocalDateTime dateTime, String temperature) {
	        //Phụ thuộc vào ban ngày hay ban đêm. Nhiệt độ thấp thì có tuyết
	    	Object[] conditions;
	        int hour = dateTime.getHour();
	        if (hour >= 6 && hour < 18) {
	            conditions = DAY_WEATHER_CONDITIONS.keySet().toArray();
	        } else {
	            conditions = NIGHT_WEATHER_CONDITIONS.keySet().toArray();
	        }
	        double tempCelsius = Double.parseDouble(temperature.replace("°C", "").trim());
	        List<String> possibleConditions = new ArrayList<>();
	        for (Object condition : conditions) {
	            String conditionStr = condition.toString().toLowerCase();
	            if (tempCelsius < 0) {
	                if (conditionStr.contains("snow") || conditionStr.contains("ice")) {
	                    possibleConditions.add(condition.toString());
	                }
	            } else {
	                if (!conditionStr.contains("snow") && !conditionStr.contains("ice")) {
	                    possibleConditions.add(condition.toString());
	                }
	            }
	        }
	        return possibleConditions.isEmpty() ? null : possibleConditions.get(random.nextInt(possibleConditions.size()));
	    }
	    public List<Weather_Hourly> getHourlyWeather(String city) {/////////
	    	 WeatherApiResponse weatherResponse = fetchWeatherFromExternalApi(city);
	    	// Lấy ngày đúng của khu vực quốc gia từ dữ liệu API
	    	    String localDateStr = weatherResponse.getCurrent().getLast_updated().substring(0, 10);
	    	    LocalDate localDate = LocalDate.parse(localDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
	    	    // Lấy dữ liệu thời tiết cho ngày đúng của khu vực quốc gia
	    	    List<Weather_Hourly> weatherList = weatherRepository.findByCityAndDate(city, localDate);
	    	    if (weatherList.isEmpty()) { // Nếu không có dữ liệu, cập nhật lại từ API và lưu vào cơ sở dữ liệu
	    	       
	    	        updateHourlyWeather(city,weatherResponse);
	    	        // Lấy lại dữ liệu từ cơ sở dữ liệu sau khi đã cập nhật
	    	        weatherList = weatherRepository.findByCityAndDate(city, localDate);
	    	    }
	    	    else //nếu đã có rồi thì kiểm tra xem giờ đã được cập nhật mới chưa, xóa những giờ cũ đi.
	    	    {
	    	    	   String currentTimeStr = weatherResponse.getCurrent().getLast_updated();
	    		        LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	    	        for (Weather_Hourly weatherHourly : weatherList) {
	    	            int hourlyTime = Integer.parseInt(weatherHourly.getTime().substring(0, 2)); // Lấy giá trị giờ từ chuỗi
	    	            // Kiểm tra xem mốc thời gian trong cơ sở dữ liệu có còn hiện tại không
	    	            if (hourlyTime<=currentTime.getHour()) {
	    	                // Nếu thời gian trong cơ sở dữ liệu đã qua, xóa mục đó
	    	                weatherRepository.delete(weatherHourly);
	    	            }
	    	        }
	    	       
	    	        // Lấy lại dữ liệu từ cơ sở dữ liệu sau khi đã cập nhật
	    	        weatherList = weatherRepository.findByCityAndDate(city, localDate);
		           
	    	    }
	    	    return weatherList;
	    }
	
	    private WeatherApiResponse fetchWeatherFromExternalApi(String city) {
	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            String url = API_URL.replace("{city}", city);
	            return restTemplate.getForObject(url, WeatherApiResponse.class);
	        } catch (Exception e) {
	            // Xử lý lỗi nếu có
	            e.printStackTrace();
	            return null;
	        }
	    }
	    				/***7 ngày tiếp theo***/
	    
	//Check xem trong database đã lưu thời gian hiện tại chưa hay vẫn còn giữ dữ liệu của ngày cũ để xóa đi cập nhật lại:
	    @Scheduled(cron = "0 0 0 * * ?") // Mỗi ngày vào 0:00
	    public void updateDailyWeatherForecast(String city,WeatherApiResponse currentWeather) {

	        // Thực hiện logic tính toán thông tin thời tiết của 7 ngày tiếp theo dựa trên dữ liệu hiện tại và lưu vào cơ sở dữ liệu
	        // Lưu ý: thực hiện logic tính toán cho thông tin thời tiết của 7 ngày tiếp theo ở đây
	        // Sau đó, lưu vào cơ sở dữ liệu bằng cách sử dụng Weather_Daily và Weather_Repo
	    	// Lấy thông tin thời tiết hiện tại cho thành phố

	        if (currentWeather != null) {
	            // Lấy ngày hiện tại
	        	 // Lấy thời gian hiện tại từ dữ liệu thời tiết
	            String currentTimeStr = currentWeather.getCurrent().getLast_updated();
	            LocalDateTime currentDateTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	            // Lấy ngày hiện tại của thành phố từ thời gian hiện tại
	            LocalDate currentDate = currentDateTime.toLocalDate();
	            
	            // Xác định ngày của 7 ngày tiếp theo
	            List<LocalDate> next7Days = new ArrayList<>();
	            for (int i = 1; i <= 7; i++) {
	                next7Days.add(currentDate.plusDays(i));
	            }
	            
	            // Tính toán và lưu thông tin thời tiết của 7 ngày tiếp theo vào cơ sở dữ liệu
	            for (LocalDate date : next7Days) {
	                // Thực hiện tính toán thông tin thời tiết cho ngày hiện tại và lưu vào cơ sở dữ liệu
	                Weather_Nextday weatherNextday = calculateNextdayWeather(currentWeather, date,city);
	                weatherNextday_Repository.save(weatherNextday);
	            }
	        }
	    }
	
	// Thêm phương thức getDailyWeatherForecast
	    public List<Weather_Nextday> getDailyWeatherForecast(String city) {
	    
	    	WeatherApiResponse currentWeather = fetchWeatherFromExternalApi(city);
	        // Lấy thông tin dự báo thời tiết cho 7 ngày tiếp theo từ cơ sở dữ liệu
	        List<Weather_Nextday> forecast = weatherNextday_Repository.findDailyWeatherForecast(city);

	        // Lấy thời gian hiện tại từ dữ liệu thời tiết
	        String currentTimeStr = currentWeather.getCurrent().getLast_updated();
	        LocalDateTime currentDateTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        // Lấy ngày hiện tại của thành phố từ thời gian hiện tại
	        LocalDate currentDate = currentDateTime.toLocalDate();

	        // Lọc bỏ những ngày đã qua
	        for (Weather_Nextday weatherNextday : forecast) {
	            if (weatherNextday.getDate().isBefore(currentDate)) {
	                weatherNextday_Repository.delete(weatherNextday);
	            }
	        }

	        // Lấy lại dự báo thời tiết sau khi xóa những ngày đã qua
	        forecast = weatherNextday_Repository.findDailyWeatherForecast(city);

	        // Nếu không có dữ liệu thời tiết dự báo cho 7 ngày tiếp theo trong cơ sở dữ liệu
	        if (forecast.isEmpty()) {
	            // Cập nhật thông tin thời tiết dự báo từ nguồn dữ liệu bên ngoài
	            updateDailyWeatherForecast(city, currentWeather);
	            // Sau khi cập nhật, truy vấn lại cơ sở dữ liệu để lấy dữ liệu mới
	            forecast = weatherNextday_Repository.findDailyWeatherForecast(city);
	        }

	        return forecast;
	    }
	    private Weather_Nextday calculateNextdayWeather(WeatherApiResponse currentWeather, LocalDate date, String city) {
	    	 String currentTimeStr = currentWeather.getCurrent().getLast_updated();
	            LocalDateTime currentDateTime = LocalDateTime.parse(currentTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	    	// Lấy nhiệt độ hiện tại từ dữ liệu thời tiết hiện tại
	        double currentTemperature = currentWeather.getCurrent().getTemp_c();

	        // Tính toán nhiệt độ trung bình và nhiệt độ tối đa/tối thiểu dựa trên quy luật đã mô tả
	        double Temp_avg = 0;
	        int currentHour = LocalDateTime.now().getHour();
	        if (currentDateTime.getHour() >= 0 && currentDateTime.getHour() <= 5)
	            Temp_avg = currentTemperature - Math.sin(((2 * Math.PI) / 24) * (currentHour - 14)) * 4;
	        else
	            Temp_avg = currentTemperature - Math.sin(((2 * Math.PI) * (currentHour - 14) / 24) - (49 * Math.PI) / 32) * 4;

	        double currentMaxTemperature = Temp_avg + 4 * Math.sin(((2 * Math.PI) * (14 - 14) / 24) - (49 * Math.PI) / 32);
	        double currentMinTemperature = Temp_avg + 4 * Math.sin(((2 * Math.PI) * (4 - 14) / 24));

	        // Random nhiệt độ tối đa và tối thiểu cho ngày tiếp theo
	        double nextMaxTemperature = currentMaxTemperature + (random.nextDouble() * 4); // Giả sử tăng khoảng 1-3 độ C
	        double nextMinTemperature = currentMinTemperature + (random.nextDouble() * 4); // Giả sử tăng khoảng 1-3 độ C

	        // Tính toán khả năng mưa và xác định điều kiện thời tiết cho ngày tiếp theo
	        int chanceOfRain = random.nextInt(90) + 1;
//	        String condition;
//	        if (chanceOfRain > 70) {
//	            condition = "Rain";
//	        } else {
//	            condition = "Sunny";
//	        }

	        // Tạo đối tượng Weather_Nextday với thông tin tính toán
	        Weather_Nextday nextdayWeather = new Weather_Nextday();
	        nextdayWeather.setDate(date);
	        nextdayWeather.setMaxTemperature(nextMaxTemperature);
	        nextdayWeather.setMinTemperature(nextMinTemperature);
	        nextdayWeather.setChanceOfRain(chanceOfRain);
	        nextdayWeather.setDayOfWeek(date.getDayOfWeek().toString());
	        nextdayWeather.setCity(city);  // Set the city attribute
	        return nextdayWeather;
	    }
	    
	
	}
