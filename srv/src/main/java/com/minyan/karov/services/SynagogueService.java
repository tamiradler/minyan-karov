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
	
	static List <Synagogue> synagogues = new ArrayList<>(); 
	
	@CrossOrigin
	@GetMapping("/getAllSynagogues")
  	public GetAllSynagoguesOutput getAllSynagogues()
	{
		GetAllSynagoguesOutput getAllSynagoguesOutput = new GetAllSynagoguesOutput();
		try 
		{
			Synagogue s = new Synagogue();
			s.setSynagogueId("yyyyyyyyyy");
			s.setSynagogueName("אוהל בלהה");
			s.setCoordinate("32.086718:34.789760");
			
			Minyan m = new Minyan();
			s.addMinyan(m);
			m.setMinyanId("sdfsdf");
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
	
	
	
	
	@Autowired
	private ApplicationContext context;
	
	@CrossOrigin
	@PostMapping("/addSynagogue")
  	public AddSynagogueOutput addSynagogue(@RequestBody AddSynagogueInput addSynagogueInput)
	{
		AddSynagogueOutput addSynagogueOutput = new AddSynagogueOutput();
		try 
		{
			SynagogueValidator synagogueValidator = new SynagogueValidator();
			synagogueValidator.validate(addSynagogueInput.getSynagogue(), addSynagogueOutput);
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
