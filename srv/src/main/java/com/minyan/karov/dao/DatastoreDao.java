package com.minyan.karov.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;



public abstract class DatastoreDao 
{
	@Autowired
	IdGenerator idGenerator;
	
	

	
	protected abstract void put(List<EntityObject> entityObject);
	
	
	protected abstract List<EntityObject> getEntityObject(String entityName, Map <String, Set<String>> data);
	
	
	public <T>List<T> getEntity(Class <T> entityClass, Entry<String,String> ... pair)
	{
		Map <String, Set<String>> data = new HashMap<>();
		
		for (Entry<String, String> p : pair) 
		{
			Set <String> values = null;
			if(data.containsKey(p.getKey()))
			{
				values = data.get(p.getKey());
			}
			else
			{
				values = new HashSet<>();
				data.put(p.getKey(), values);
			}
			values.add(p.getValue());
		}
		
		List<T> objs = new ArrayList<>();
		try 
		{
			String entityName = getEntityNameFronClass(entityClass);
			List<EntityObject> entityObjects = getEntityObject(entityName, data);
			
			for (EntityObject entityObject : entityObjects)
			{
				T obj = entityClass.getDeclaredConstructor().newInstance();
				
				for (Entry<String, Object> entry : entityObject.getProperties().entrySet())
				{
					Field f = getField(entityClass, entry.getKey());
					f.setAccessible(true);
					f.set(obj, entry.getValue());
				}
				
				objs.add(obj);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return objs;
	}
	
	
	private Field getField(Class<?>clazz, String fieldName)
	{
		for (Field f : clazz.getDeclaredFields())
		{
			Column column = f.getAnnotation(Column.class);
			if (column != null)
			{
				String columnName = column.name() != null && !column.name().trim().isEmpty() ? column.name() : f.getName();
				if (columnName.equals(fieldName))
				{
					return f;
				}
			}
		}
		return null;
	}
	
	
	public void create(Object obj) throws Exception
	{
		List<EntityObject> entityObjects = new ArrayList<>();
		
		if (obj instanceof Collection<?>)
		{
			Collection <?> collection = (Collection<?>) obj;
			for (Object object : collection) 
			{
				entityObjects.addAll(populateFields(object));
			}
		}
		else
		{
			entityObjects.addAll(populateFields(obj));
		}
		put(entityObjects);     
	}
	
	
	private String getEntityNameFronClass(Class <?> clazz)
	{
		javax.persistence.Entity entity = clazz.getAnnotation(javax.persistence.Entity.class);
		if (entity == null)
		{
			return null;
		}
		
		return entity.name();		
	}
	
	
	private List<EntityObject> populateFields(Object obj) throws Exception
	{
		List<EntityObject> entityObjects = new ArrayList<EntityObject>();
		
		String entityName = getEntityNameFronClass(obj.getClass());
		
		EntityObject entityObject = new EntityObject();
		entityObjects.add(entityObject);
		entityObject.setEntityName(entityName);
		
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
		
		entityObject.setId(getObjectId(obj));
		
		if (entityObject.getId() == null)
		{
			throw new Exception("entityObject.getId() should not be null.");
		}
		
		return entityObjects;
	}
	
	
	private String getObjectId(Object obj) throws Exception
	{
		for (Field f : obj.getClass().getDeclaredFields())
		{
			Id id = f.getAnnotation(Id.class);
			if (id != null)
			{
				f.setAccessible(true);
				String value = (String) f.get(obj);
				return value;
			}
		}
		
		for (Method method : obj.getClass().getDeclaredMethods())
		{
			Id id = method.getAnnotation(Id.class);
			if (id != null)
			{
				method.setAccessible(true);
				String value = (String) method.invoke(obj);
				return value;
			}
		}
		
		return null;
	}
	
	
	public void update(Object obj) throws Exception
	{
		List<EntityObject> entityObjects = populateFields(obj);
		update(entityObjects);     
	}
	
	abstract void update(List<EntityObject> entityObjects);
}
