package com.minyan.karov.services.synagogue.minyanfilter;

import java.util.Iterator;
import java.util.List;

public class FiltersService<ELEMENT_TO_FILTER, ARGS>
{
	private List<Filter<ELEMENT_TO_FILTER, ARGS>> filters;
	
	private ARGS args;
	
	private List<ELEMENT_TO_FILTER> elementsToFilter;

	public void setArgs(ARGS args) 
	{
		this.args = args;
	}

	public void setElementsToFilter(List<ELEMENT_TO_FILTER> elementsToFilter) 
	{
		this.elementsToFilter = elementsToFilter;
	}
	
	public List<ELEMENT_TO_FILTER> getFilteredElements()
	{
		return this.elementsToFilter;
	}

	public void setFilters(List<Filter<ELEMENT_TO_FILTER, ARGS>> filters)
	{
		this.filters = filters;
	}
	
	public void execute()
	{
		for (Iterator<ELEMENT_TO_FILTER> iterator = elementsToFilter.iterator(); iterator.hasNext();) 
		{
			ELEMENT_TO_FILTER element = iterator.next();
			if (!isValied(element))
			{
				iterator.remove();
			}
		}
	}
	
	public boolean isValied(ELEMENT_TO_FILTER element)
	{
		for (Filter<ELEMENT_TO_FILTER, ARGS> filter : filters) 
		{
			if (!filter.isValied(args, element))
			{
				return false;
			}
		}
		return true;
	}
}
