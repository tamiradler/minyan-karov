package com.minyan.karov.services.synagogue.minyanfilter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.minyan.karov.entities.Minyan;

@Service
@Scope("prototype")
public class MinyanFiltersService 
{
	private List<Minyan> minyans;

	private MinyanParameters minyanParameters;
	
	@Autowired
	private List<Filter<Minyan, MinyanParameters>> minyanFilters;
	
	public MinyanParameters getMinyanParameters() 
	{
		return minyanParameters;
	}

	public void setMinyanParameters(MinyanParameters minyanParameters) 
	{
		this.minyanParameters = minyanParameters;
	}

	public List<Minyan> getMinyans() 
	{
		return minyans;
	}

	public void setMinyans(List<Minyan> minyans) 
	{
		this.minyans = minyans;
	}
	
	public void execute()
	{
		if (minyanParameters == null)
		{
			return;
		}
		
		FiltersService<Minyan, MinyanParameters> filtersService = new FiltersService<>();
		filtersService.setArgs(minyanParameters);
		filtersService.setElementsToFilter(minyans);
		filtersService.setFilters(minyanFilters);
		filtersService.execute();
		minyans = filtersService.getFilteredElements();
	}
}
