package com.minyan.karov.services.synagogue.minyanfilter;

import java.util.Iterator;
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
	List<MinyanFilter> minyanFilters;
	
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
		
		for (Iterator<Minyan> minyanIterator = minyans.iterator(); minyanIterator.hasNext(); ) 
		{
			Minyan minyan = minyanIterator.next();
			if (!isMinyanValid(minyan))
			{
				minyanIterator.remove();
			}
		}
	}

	private boolean isMinyanValid(Minyan minyan) 
	{
		for (MinyanFilter minyanFilter : minyanFilters) 
		{
			if (!minyanFilter.isValied(minyanParameters, minyan))
			{
				return false;
			}
		}
		return true;
	}
}
