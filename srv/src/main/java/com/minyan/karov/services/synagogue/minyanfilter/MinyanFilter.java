package com.minyan.karov.services.synagogue.minyanfilter;

import com.minyan.karov.entities.Minyan;

public interface MinyanFilter 
{
	boolean isValied(MinyanParameters minyanParameters, Minyan minyan);
}
