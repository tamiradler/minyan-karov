package com.minyan.karov.services.zmanim;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ZmanimService 
{
	@CrossOrigin
	@GetMapping("/getZmanim/latLng/{latLng}/year/{year}/day/{day}/month/{month}")
	GetZmanimOutput getZmanim(
			@PathVariable(value="latLng") String latLng,
			@PathVariable(value="year") double year,
			@PathVariable(value="day") double day,
			@PathVariable(value="month") double month
			)
	{
		RestTemplate restTemplate = new RestTemplate();
		TimeZoneDb timeZoneDb = restTemplate.getForObject("http://api.timezonedb.com/v2/get-time-zone?key=OAURQ36AWJEN&format=json&by=position&lat=32.086718&lng=34.789760", TimeZoneDb.class);
		
		
		Zmanim zmanim = new Zmanim();
		zmanim.setLatLng(latLng);
		zmanim.setDate(year, month, day);
		zmanim.setGmtOffset(timeZoneDb.getGmtOffset()/60.0/60.0);
		
		ZmanimOutput zmanimOutput = new ZmanimOutput();
		GetZmanimOutput getZmanimOutput = new GetZmanimOutput();
		getZmanimOutput.setZmanimOutput(zmanimOutput);
		
		zmanimOutput.setNoon(zmanim.getNoon());
		zmanimOutput.setSunrise(zmanim.getSunriseTime());
		zmanimOutput.setSunset(zmanim.getSunsetTime());
		zmanimOutput.setShaaZmanit(String.valueOf(zmanim.getShaaZmanit()));
		zmanimOutput.setTzetHkohavim(zmanim.getTzetHkohavim());
		zmanimOutput.setAlotHashahar(zmanim.getAlotHashahar());
		return getZmanimOutput;
	}
	
	
	@CrossOrigin
	@GetMapping("/getV")
	String getZmanim()
	{
		return System.getenv("JAVA_HOME");
	}
}
