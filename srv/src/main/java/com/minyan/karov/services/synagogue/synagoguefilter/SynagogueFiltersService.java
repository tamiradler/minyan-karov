package com.minyan.karov.services.synagogue.synagoguefilter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.minyan.karov.entities.Minyan;
import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.services.synagogue.minyanfilter.Filter;
import com.minyan.karov.services.synagogue.minyanfilter.FiltersService;
import com.minyan.karov.services.synagogue.minyanfilter.MinyanParameters;

@Service
@Scope("prototype")
public class SynagogueFiltersService 
{
	@Autowired
	private List<Filter<Synagogue, MinyanParameters>> synagogueFilters;
	
	private List<Synagogue> synagogues;
	
	private MinyanParameters minyanParameters;

	public void setSynagogues(List<Synagogue> synagogues) 
	{
		this.synagogues = synagogues;
	}

	public List<Synagogue> getSynagogues()
	{
		return this.synagogues;
	}
	
	public void setMinyanParameters(MinyanParameters minyanParameters) 
	{
		this.minyanParameters = minyanParameters;
	}

	public void execute()
	{
		if (minyanParameters == null)
		{
			return;
		}
		
		FiltersService<Synagogue, MinyanParameters> filtersService = new FiltersService<>();
		filtersService.setArgs(minyanParameters);
		filtersService.setElementsToFilter(synagogues);
		filtersService.setFilters(synagogueFilters);
		filtersService.execute();
		synagogues = filtersService.getFilteredElements();
	}
}
