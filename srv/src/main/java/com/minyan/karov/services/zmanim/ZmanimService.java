package com.minyan.karov.services.zmanim;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZmanimService 
{
	@CrossOrigin
	@GetMapping("/getZmanim/latLng/{latLng}/year/{year}/day/{day}/month/{month}/gmtOffset/{gmtOffset}")
	GetZmanimOutput getZmanim(
			@PathVariable(value="latLng") String latLng,
			@PathVariable(value="year") double year,
			@PathVariable(value="day") double day,
			@PathVariable(value="month") double month,
			@PathVariable(value="gmtOffset") double gmtOffset
			)
	{
		Zmanim zmanim = new Zmanim();
		zmanim.setLatLng(latLng);
		zmanim.setDate(year, month, day);
		zmanim.setGmtOffset(gmtOffset);
		
		ZmanimOutput zmanimOutput = new ZmanimOutput();
		GetZmanimOutput getZmanimOutput = new GetZmanimOutput();
		getZmanimOutput.setZmanimOutput(zmanimOutput);
		
		zmanimOutput.setNoon(zmanim.getNoon());
		zmanimOutput.setSunrise(zmanim.getSunriseTime());
		zmanimOutput.setSunset(zmanim.getSunsetTime());
		zmanimOutput.setShaaZmanit(String.valueOf(zmanim.getShaaZmanit()));
		
		return getZmanimOutput;
	}
}
