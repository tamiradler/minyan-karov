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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minyan.karov.dao.DatastoreDao;
import com.minyan.karov.dao.IdGenerator;
import com.minyan.karov.entities.Minyan;
import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.entities.validators.SynagogueValidator;
import com.minyan.karov.services.synagogue.minyanfilter.MinyanFiltersService;
import com.minyan.karov.services.synagogue.minyanfilter.MinyanParameters;

@RestController
public class SynagogueService {
	
	@Autowired
	SynagogueValidator synagogueValidator;
	
	@Autowired
	DatastoreDao datastoreDao;	
	
	@Autowired
	IdGenerator idGenerator;
	
	
	@CrossOrigin
	@GetMapping("/getSynagogue/{synagogueId}")
  	public GetAllSynagoguesOutput getSynagogue(@PathVariable(value="synagogueId") String synagogueId)
	{
		GetAllSynagoguesOutput getAllSynagoguesOutput = new GetAllSynagoguesOutput();
		try 
		{
			Entry<String, String> pair = new AbstractMap.SimpleEntry<String, String>("synagogueId", synagogueId);
			List <Synagogue> synagogues = datastoreDao.getEntity(Synagogue.class, pair);
			List <String> synagogueIds = getSynagogueIds(synagogues);
			Map<String, List<Minyan>> synagogueIdToMinyan = getSynagogueIdToMinyan(synagogueIds, null);
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
	
	
	@CrossOrigin
	@GetMapping("/getAllSynagogues")
  	public GetAllSynagoguesOutput getAllSynagogues(
  			@RequestParam(required = false) String minyanType,
  			@RequestParam(required = false) String nosach)
	{
		GetAllSynagoguesOutput getAllSynagoguesOutput = new GetAllSynagoguesOutput();
		try 
		{
			List <Synagogue> synagogues = datastoreDao.getEntity(Synagogue.class);
			List <String> synagogueIds = getSynagogueIds(synagogues);
			
			MinyanParameters minyanParameters = new MinyanParameters();
			minyanParameters.setMinyanType(minyanType);
			minyanParameters.setNosach(nosach);
			
			Map<String, List<Minyan>> synagogueIdToMinyan = getSynagogueIdToMinyan(synagogueIds, minyanParameters);
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
	
	
	
	
	private Map<String, List<Minyan>> getSynagogueIdToMinyan(List<String> synagogueIds, MinyanParameters minyanParameters) 
	{
		Map <String, List<Minyan>> synagogueIdToMinyan = new HashMap<>();
		List <Minyan> minyans = getMinyans(synagogueIds, minyanParameters);
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


	@Autowired
	private ApplicationContext context;

	private List<Minyan> getMinyans(List<String> synagogueIds, MinyanParameters minyanParameters) 
	{
		List<Entry<String, String>> pairs = new ArrayList<>();// = new AbstractMap.SimpleEntry<String, String>("userId", userId);
		
		for (String synagogueId : synagogueIds) 
		{
			pairs.add(new AbstractMap.SimpleEntry<String, String>("senagogId", synagogueId));
		}

		List<Minyan> minyans = datastoreDao.getEntity(Minyan.class, pairs.toArray(new Entry[pairs.size()]));
		
		MinyanFiltersService minyanFiltersService = context.getBean(MinyanFiltersService.class);
		minyanFiltersService.setMinyanParameters(minyanParameters);
		minyanFiltersService.setMinyans(minyans);
		
		minyanFiltersService.execute();
		
		return minyanFiltersService.getMinyans();
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
