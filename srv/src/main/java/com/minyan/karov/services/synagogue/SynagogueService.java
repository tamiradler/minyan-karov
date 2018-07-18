package com.minyan.karov.services.synagogue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.minyan.karov.dao.DatastoreDao;
import com.minyan.karov.dao.IdGenerator;
import com.minyan.karov.entities.Minyan;
import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.entities.User;
import com.minyan.karov.entities.validators.SynagogueValidator;

@RestController
public class SynagogueService {
	
	@Autowired
	SynagogueValidator synagogueValidator;
	
	@Autowired
	DatastoreDao datastoreDao;	
	
	@Autowired
	IdGenerator idGenerator;
	
	@CrossOrigin
	@GetMapping("/getAllSynagogues")
  	public GetAllSynagoguesOutput getAllSynagogues()
	{
		GetAllSynagoguesOutput getAllSynagoguesOutput = new GetAllSynagoguesOutput();
		try 
		{
			List <Synagogue> synagogues = datastoreDao.getEntity(Synagogue.class);
			List <String> synagogueIds = getSynagogueIds(synagogues);
			Map<String, List<Minyan>> synagogueIdToMinyan = getSynagogueIdToMinyan(synagogueIds);
			for (Synagogue synagogue : synagogues) 
			{
				synagogue.setMinyans(synagogueIdToMinyan.get(synagogue.getSynagogueId()));
			}
			
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
	
	
	
	
	private Map<String, List<Minyan>> getSynagogueIdToMinyan(List<String> synagogueIds) 
	{
		Map <String, List<Minyan>> synagogueIdToMinyan = new HashMap<>();
		List <Minyan> minyans = getMinyans(synagogueIds);
		for (Minyan minyan : minyans) 
		{
			List<Minyan> list = synagogueIdToMinyan.get(minyan.getSenagogId());
			if (list == null)
			{
				list = new ArrayList<>();
				synagogueIdToMinyan.put(minyan.getSenagogId(), list);
			}
			list.add(minyan);
		}
		return synagogueIdToMinyan;
	}




	private List<Minyan> getMinyans(List<String> synagogueIds) 
	{
		
		List<Entry<String, String>> pairs = new ArrayList<>();// = new AbstractMap.SimpleEntry<String, String>("userId", userId);
		
		for (String synagogueId : synagogueIds) 
		{
			pairs.add(new AbstractMap.SimpleEntry<String, String>("senagogId", synagogueId));
		}

		return datastoreDao.getEntity(Minyan.class, pairs.toArray(new Entry[pairs.size()]));
	}




	private List<String> getSynagogueIds(List<Synagogue> synagogues) 
	{
		List<String> synagogueIds = new ArrayList<>();
		for(Synagogue synagogue : synagogues)
		{
			synagogueIds.add(synagogue.getSynagogueId());
		}
		return synagogueIds;
	}




	@CrossOrigin
	@PostMapping("/addSynagogue")
  	public AddSynagogueOutput addSynagogue(@RequestBody AddSynagogueInput addSynagogueInput)
	{
		AddSynagogueOutput addSynagogueOutput = new AddSynagogueOutput();
		try 
		{
			Synagogue synagogue = addSynagogueInput.getSynagogue();
			synagogueValidator.validate(synagogue, addSynagogueOutput);
			if (addSynagogueOutput.getErrors() != null && addSynagogueOutput.getErrors().size() > 0) {
				return addSynagogueOutput;
			}
			
			populateIds(synagogue);
			
			datastoreDao.create(synagogue);
		}
		catch (Exception e) 
		{
			addSynagogueOutput.addThrowable(e);
		}
		
		return addSynagogueOutput;
	}


	private void populateIds(Synagogue synagogue)
	{
		synagogue.setSynagogueId(idGenerator.getUniqueId());
		
		List <Minyan> minyans = synagogue.getMinyans();
		if (minyans == null || minyans.size()==0)
		{
			return;
		}
		
		for (Minyan minyan : minyans)
		{
			minyan.setMinyanId(idGenerator.getUniqueId());
			minyan.setSenagogId(synagogue.getSynagogueId());
		}
	}
}
