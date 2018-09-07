package com.minyan.karov.services.synagogue.synagoguefilter;

import org.springframework.stereotype.Service;

import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.services.synagogue.minyanfilter.MinyanParameters;

@Service
public class SynagogueFilterByNosach implements SynagogueFilter
{

	@Override
	public boolean isValied(MinyanParameters args, Synagogue elementToFilter) 
	{
		if (args.getNosach().equals("all"))
		{
			return true;
		}
		return args.getNosach().equals(elementToFilter.getNosach());
	}

}
