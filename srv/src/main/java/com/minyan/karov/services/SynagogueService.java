package com.minyan.karov.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.minyan.karov.entities.Minyan;
import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.entities.validators.SynagogueValidator;

@RestController
public class SynagogueService {
	
	@Autowired
	SynagogueValidator synagogueValidator;
	
	
	static List <Synagogue> synagogues = new ArrayList<>(); 
	
	@CrossOrigin
	@GetMapping("/getAllSynagogues")
  	public GetAllSynagoguesOutput getAllSynagogues()
	{
		GetAllSynagoguesOutput getAllSynagoguesOutput = new GetAllSynagoguesOutput();
		try 
		{
			getAllSynagoguesOutput.setSynagogues(synagogues);
		}
		catch (Exception e) 
		{
			String str = e.toString() + "<br/>";
			for (StackTraceElement stackTraceElement : e.getStackTrace())
			{
				str = str + stackTraceElement.toString() + "<br/>";
			}
			return getAllSynagoguesOutput;
		}
		
		
		
		return getAllSynagoguesOutput;
	}
	
	
	
	
	@CrossOrigin
	@PostMapping("/addSynagogue")
  	public AddSynagogueOutput addSynagogue(@RequestBody AddSynagogueInput addSynagogueInput)
	{
		AddSynagogueOutput addSynagogueOutput = new AddSynagogueOutput();
		try 
		{
			synagogueValidator.validate(addSynagogueInput.getSynagogue(), addSynagogueOutput);
			if (addSynagogueOutput.getErrors() != null && addSynagogueOutput.getErrors().size() > 0) {
				return addSynagogueOutput;
			}
			synagogues.add(addSynagogueInput.getSynagogue());
		}
		catch (Exception e) 
		{
			String str = e.toString() + "<br/>";
			for (StackTraceElement stackTraceElement : e.getStackTrace())
			{
				str = str + stackTraceElement.toString() + "<br/>";
			}
			return addSynagogueOutput;
		}
		
		return addSynagogueOutput;
	}
}
