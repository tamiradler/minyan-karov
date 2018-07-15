package com.minyan.karov.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Repository
@Profile("production")
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
			Entity entity = new Entity(entityObject.getEntityName(), entityObject.getId());
			entities.add(entity);
			for (Entry<String, Object> entry : entityObject.getProperties().entrySet())
			{
				entity.setProperty(entry.getKey(), entry.getValue());
			}
		}
		
		return entities;
	}
	
	
	@Override
	protected List<EntityObject> getEntityObject(String entityName, Map <String, Set<String>> data)
	{
		List<EntityObject> entityObjects = new ArrayList<>();
		Query query = new Query(entityName);
		
		for (Entry<String, Set<String>> p : data.entrySet())
		{
			Filter filter = new FilterPredicate(p.getKey(), FilterOperator.IN, p.getValue());
			query.setFilter(filter);
		}
		
		List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		for (Entity entity : results)
		{
			EntityObject entityObject = new EntityObject();
			entityObject.setProperties(entity.getProperties());
			entityObject.setEntityName(entity.getKind());
			entityObjects.add(entityObject);
		}
		return entityObjects;
	}
	
	@Override
	public void update(List<EntityObject> entityObjects)
	{
		List<Entity> posts = populateFields(entityObjects);
		datastore.put(posts);
	}
}
