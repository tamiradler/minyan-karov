package com.minyan.karov.services.zmanim;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

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
		ZoneId zoneId = ZoneId.systemDefault();
		long unixTime = LocalDate.of( (int) year , Month.of((int) month) , (int) day ).atStartOfDay(zoneId).toEpochSecond();
		
		String [] latLngArr = latLng.split(",");
		RestTemplate restTemplate = new RestTemplate();
		TimeZoneDb timeZoneDb = restTemplate.getForObject("http://api.timezonedb.com/v2/get-time-zone?"
					+ "key="+System.getenv("TIME_ZONE_DB_KEY")
					+ "&format=json"
					+ "&by=position"
					+ "&lat=" + latLngArr[0]
					+ "&lng=" + latLngArr[1]
					+ "&time=" + unixTime, TimeZoneDb.class);
		
		
		
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
		zmanimOutput.setShaaZmanit(zmanim.getShaaZmanitTime());
		zmanimOutput.setTzetHkohavim(zmanim.getTzetHkohavim());
		zmanimOutput.setAlotHashahar(zmanim.getAlotHashahar());
		return getZmanimOutput;
	}
	
	
	@CrossOrigin
	@GetMapping("/getV")
	String getZmanim()
	{
		return System.getenv("TIME_ZONE_DB_KEY");
	}
}
