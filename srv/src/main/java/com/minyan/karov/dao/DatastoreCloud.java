package com.minyan.karov.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

@Repository
@Profile("dev")
public class DatastoreCloud extends DatastoreDao
{
	private Datastore datastore;
	
	
	public DatastoreCloud() 
	{
		datastore = DatastoreOptions.getDefaultInstance().getService();
	}
	
	
	@Override
	protected void put(List<EntityObject> entityObjects)
	{
		List <FullEntity<?>> tasks = getTasksFromEntityObjects(entityObjects);
		datastore.put(tasks.toArray(new FullEntity[tasks.size()]));	
	}
	
	
	List <FullEntity<?>> getTasksFromEntityObjects(List<EntityObject> entityObjects)
	{
		List <FullEntity<?>> tasks = new ArrayList<FullEntity<?>>();
		for (EntityObject entityObject : entityObjects)
		{
			Key taskKey = datastore.newKeyFactory().setKind(entityObject.getEntityName()).newKey(entityObject.getId());
			Builder builder = Entity.newBuilder(taskKey);
			
			Entity task = populateFields(entityObject, builder);
			tasks.add(task);
		}
		return tasks;
	}
	
	
	private Entity populateFields(EntityObject entityObject, Builder builder)
	{
		for (Entry<String, Object> entry : entityObject.getProperties().entrySet())
		{
			builder.set(entry.getKey(), (String) entry.getValue());
		}
		return builder.build();
	}
	
	
	
	private List <String> getStringQueries(String entityName, Map <String, Set<String>> data, Map <String, String> args)
	{
		List <String> queries = new ArrayList<>();
		int argPos = 0;
		String select = "SELECT * FROM " + entityName;
		if (data.isEmpty())
		{
			queries.add(select);
			return queries;
		}
		select = select + " WHERE";
		for (Entry<String, Set<String>> p : data.entrySet())
		{
			List <String> q1 = new ArrayList<>();
			for (String val : p.getValue())
			{
				argPos++;
				args.put("par" + argPos, val);
				String q = " " + p.getKey()+" = @par" + argPos;
				q1.add(q);
			}
			
			if (queries.isEmpty())
			{
				queries=q1;
			}
			else
			{
				List <String> q2 = new ArrayList<>();
				for (String s : queries)
				{
					for (String q : q1)
					{
						q2.add(s + " AND " + q);
					}
				}
				queries.clear();
				queries = q2;
			}
		}
		List <String> finalQueries = new ArrayList<>();
		for (String q : queries)
		{
			finalQueries.add(select + q);
		}
		return finalQueries;
	}
	
	
	private List<Query<?>> getQueries(String entityName, Map <String, Set<String>> data)
	{
		List<Query<?>> queries = new ArrayList<>();
		Map <String, String> args = new HashMap<>();
		List <String> stringQueries = getStringQueries(entityName, data, args);
		
		for (String q : stringQueries)
		{
			GqlQuery.Builder <?> builder = Query.newGqlQueryBuilder(q);
			for (Map.Entry<String, String> entry : args.entrySet())
			{
				builder.setBinding(entry.getKey(), entry.getValue());
			}
			Query<?> query = builder.build();
			queries.add(builder.build());
		}
		
		return queries;
	}
	
	
	private List<QueryResults <?>> getQueryResults(String entityName, Map <String, Set<String>> data)
	{
		List<Query<?>> queries =  getQueries(entityName, data);
		List<QueryResults <?>> queryResults = new ArrayList<>();
		
		for (Query<?> q : queries)
		{
			queryResults.add(datastore.run(q));
		}
		
		return queryResults;
	}
	
	
	@Override
	protected List<EntityObject> getEntityObject(String entityName, Map <String, Set<String>> data)
	{
		List<QueryResults<?>> queryResults = getQueryResults(entityName,  data);
		List<EntityObject> entityObjects = new ArrayList<>();
		
		for (QueryResults<?> q : queryResults)
		{
			while (q.hasNext())
			{
				EntityObject entityObject = new EntityObject();
				entityObject.setEntityName(entityName);
				Entity entity = (Entity) q.next();
				for (String fieldName : entity.getNames())
				{
					entityObject.setProperty(fieldName, entity.getString(fieldName));
				}
				entityObjects.add(entityObject);
			}
		}
		
		return entityObjects;
	}
	
	@Override
	public void update(List<EntityObject> entityObjects)
	{
		List <FullEntity<?>> tasks = getTasksFromEntityObjects(entityObjects);
		
		List<Entity> entities = new ArrayList<>();
		
		for (FullEntity<?> fullEntity : tasks) 
		{
			entities.add((Entity) fullEntity);
		}
		
		datastore.put(tasks.toArray(new FullEntity[tasks.size()]));
	}
}
