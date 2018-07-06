package com.minyan.karov.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StringConfigurationService {
	
	
	
	@CrossOrigin
	@GetMapping("/getStringConfiguration")
	public Map getStringConfiguration() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();;
		Map<?,?> empMap = objectMapper.readValue(new FileInputStream("stringConfiguration.json"),Map.class);
		return empMap;
	}
	
	
	public List<?> getStringObjArray(String str) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		Map<?,?> map = getStringConfiguration();
		return (List<?>) map.get(str);
	}
	
	
	public List<String> getStringArrayForLanguage(String str, String language) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		List <String> toReturn = new ArrayList<>();
		List<?> list = getStringObjArray(str);
		for (Object obj : list)
		{
			Map <String,String> map = (Map<String, String>) obj;
			toReturn.add(map.get(language));
		}
		return  toReturn;
	}
	
	
	@CrossOrigin
	@GetMapping("/test")
	public Object test() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		return getStringArrayForLanguage("dayTimes", "code");
	}
	
}
