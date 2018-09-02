package com.minyan.karov.services.synagogue.minyanfilter;

public interface Filter <ELEMENT_TO_FILTER, ARGS>
{
	boolean isValied(ARGS args, ELEMENT_TO_FILTER elementToFilter);
}
