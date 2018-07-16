package com.minyan.karov.services;


import java.util.ArrayList;
import java.util.List;

public class ServiceOutput 
{
	List <Throw> throwables = new ArrayList<>();
	
	public List<Throw> getThrowables() 
	{
		return throwables;
	}

	public void setThrowables(List<Throw> throwables) 
	{
		this.throwables = throwables;
	}

	public void addThrowable(Throwable throwable)
	{
		Throw throw1 = new Throw();
		throw1.setThrowable(throwable);
		throw1.setType(throwable.getClass().toString());
		throwables.add(throw1);
	}
	
	class Throw
	{
		private String type;
		
		private Throwable throwable;

		public String getType() 
		{
			return type;
		}

		public void setType(String type) 
		{
			this.type = type;
		}

		public Throwable getThrowable() 
		{
			return throwable;
		}

		public void setThrowable(Throwable throwable) 
		{
			this.throwable = throwable;
		}
	}
}

