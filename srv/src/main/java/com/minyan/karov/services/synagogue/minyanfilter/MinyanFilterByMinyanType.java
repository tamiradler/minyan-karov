package com.minyan.karov.services.synagogue.minyanfilter;

import org.springframework.stereotype.Service;

import com.minyan.karov.entities.Minyan;

@Service
public class MinyanFilterByMinyanType implements MinyanFilter
{

	@Override
	public boolean isValied(MinyanParameters args, Minyan elementToFilter) {
		if (args.getMinyanType().equals("all"))
		{
			return true;
		}
		return args.getMinyanType().equals(elementToFilter.getType());
	}

}
