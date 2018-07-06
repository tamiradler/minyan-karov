package com.minyan.karov.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;



public abstract class DatastoreDao 
{
	@Autowired
	IdGenerator idGenerator;
	
	public void create(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		List<EntityObject> entityObjects = populateFields(obj);
		put(entityObjects);     
	}
	
	
	protected abstract void put(List<EntityObject> entityObject);
	
	
	private List<EntityObject> populateFields(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		List<EntityObject> entityObjects = new ArrayList<EntityObject>();
		javax.persistence.Entity entity = obj.getClass().getAnnotation(javax.persistence.Entity.class);
		if (entity == null)
		{
			return entityObjects;
		}
		
		String entityName = entity.name();				
		
		EntityObject entityObject = new EntityObject();
		entityObjects.add(entityObject);
		entityObject.setEntityName(entityName);
		entityObject.setId(idGenerator.getUniqueId());
		
		for (Field f : obj.getClass().getDeclaredFields())
		{
			Column column = f.getAnnotation(Column.class);
			if (column != null)
			{
				String columnName = column.name() != null && !column.name().trim().isEmpty() ? column.name() : f.getName();
				f.setAccessible(true);
				String value = (String) f.get(obj);
				if (value != null)
				{
					entityObject.setProperty(columnName, value);
				}
			}
			
			OneToMany oneToMany = f.getAnnotation(OneToMany.class);
			if (oneToMany != null)
			{
				f.setAccessible(true);
				Collection <?> collection = (Collection<?>) f.get(obj);
				for (Object collObj : collection)
				{
					entityObjects.addAll(populateFields(collObj));
				}
			}
		}
		
		return entityObjects;
	}
}
