package com.minyan.karov.dao;

import java.util.HashMap;
import java.util.Map;


public class EntityObject 
{
	private String entityName;


	private String id;
	
	
	private Map <String, Object> properties = new HashMap<String, Object>();
	
	
	public String getEntityName() 
	{
		return entityName;
	}


	public void setEntityName(String entityName) 
	{
		this.entityName = entityName;
	}


	public String getId() 
	{
		return id;
	}


	public void setId(String id) 
	{
		this.id = id;
	}
	
	
	public Map <String, Object> getProperties()
	{
		return properties;
	}
	
	
	public void setProperty(String key, Object value)
	{
		properties.put(key, value);
	}
}
