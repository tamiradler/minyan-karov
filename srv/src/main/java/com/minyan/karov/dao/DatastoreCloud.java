package com.minyan.karov.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.FullEntity;

@Repository
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
		List <FullEntity<?>> tasks = new ArrayList<FullEntity<?>>();
		for (EntityObject entityObject : entityObjects)
		{
			Key taskKey = datastore.newKeyFactory().setKind(entityObject.getEntityName()).newKey(entityObject.getId());
			Builder builder = Entity.newBuilder(taskKey);
			
			Entity task = populateFields(entityObject, builder);
			tasks.add(task);
		}
		datastore.put(tasks.toArray(new FullEntity[tasks.size()]));	
	}
	
	
	private Entity populateFields(EntityObject entityObject, Builder builder)
	{
		for (Entry<String, Object> entry : entityObject.getProperties().entrySet())
		{
			builder.set(entry.getKey(), (String) entry.getValue());
		}
		return builder.build();
	}
}
