package com.minyan.karov.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@Repository
public class DatastoreApi extends DatastoreDao
{
	private DatastoreService datastore;
	
	
	public DatastoreApi() 
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	
	@Override
	protected void put(List<EntityObject> entityObjects)
	{
		List<Entity> posts = populateFields(entityObjects);
		datastore.put(posts);
	}
	
	
	private List<Entity> populateFields(List<EntityObject> entityObjects)
	{
		List<Entity> entities = new ArrayList<Entity>();
		
		for (EntityObject entityObject : entityObjects)
		{
			Entity entity = new Entity(entityObject.getEntityName());
			entities.add(entity);
			for (Entry<String, Object> entry : entityObject.getProperties().entrySet())
			{
				entity.setProperty(entry.getKey(), entry.getValue());
			}
		}
		
		return entities;
	}
}
