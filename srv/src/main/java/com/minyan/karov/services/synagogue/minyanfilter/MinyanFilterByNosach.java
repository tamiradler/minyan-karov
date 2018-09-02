package com.minyan.karov.services.synagogue.minyanfilter;

import org.springframework.stereotype.Service;

import com.minyan.karov.entities.Minyan;

@Service
public class MinyanFilterByNosach implements MinyanFilter
{

	@Override
	public boolean isValied(MinyanParameters minyanParameters, Minyan minyan) 
	{
		return true;
	}

}
