package com.minyan.karov.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Minyan")
public class Minyan
{
	@Column
	private String senagogId;
	
	@Column
	private String minyanId;
	
	@Column
	private String type;
	
	@Column
	private String time;
	
	@Column
	private String isFixedTime;
	
	@Column
	private String isDakotLifney;
	
	@Column
	private String dakotLifneyAcharey;
	
	@Column
	private String dayTime;
	
	@Column
	private String isSequense;
	
	@Column
	private String kolKamaZman;
	
	@Column
	private String dayAtWeek;
	
	
	public String getSenagogId() 
	{
		return senagogId;
	}


	public void setSenagogId(String senagogId) 
	{
		this.senagogId = senagogId;
	}


	public String getMinyanId() 
	{
		return minyanId;
	}


	public void setMinyanId(String minyanId) 
	{
		this.minyanId = minyanId;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type = type;
	}


	public String getTime() 
	{
		return time;
	}


	public void setTime(String time) 
	{
		this.time = time;
	}


	public String getIsFixedTime() 
	{
		return isFixedTime;
	}


	public void setIsFixedTime(String isFixedTime) 
	{
		this.isFixedTime = isFixedTime;
	}


	public String getIsDakotLifney() 
	{
		return isDakotLifney;
	}


	public void setIsDakotLifney(String isDakotLifney) 
	{
		this.isDakotLifney = isDakotLifney;
	}


	public String getDakotLifneyAcharey() 
	{
		return dakotLifneyAcharey;
	}


	public void setDakotLifneyAcharey(String dakotLifneyAcharey) 
	{
		this.dakotLifneyAcharey = dakotLifneyAcharey;
	}


	public String getDayTime() 
	{
		return dayTime;
	}


	public void setDayTime(String dayTime) 
	{
		this.dayTime = dayTime;
	}


	public String getIsSequense() 
	{
		return isSequense;
	}


	public void setIsSequense(String isSequense) 
	{
		this.isSequense = isSequense;
	}


	public String getKolKamaZman() 
	{
		return kolKamaZman;
	}


	public void setKolKamaZman(String kolKamaZman) 
	{
		this.kolKamaZman = kolKamaZman;
	}


	public String getDayAtWeek() 
	{
		return dayAtWeek;
	}


	public void setDayAtWeek(String dayAtWeek) 
	{
		this.dayAtWeek = dayAtWeek;
	}
}
