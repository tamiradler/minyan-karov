package com.minyan.karov.dao;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator 
{
	public String getUniqueId()
	{
		return UUID.randomUUID().toString();
	}
}
