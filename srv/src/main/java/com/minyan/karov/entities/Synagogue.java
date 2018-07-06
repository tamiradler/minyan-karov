package com.minyan.karov.entities;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

@Entity(name = "Synagogue")
public class Synagogue 
{
	@Id
	@Column
	private String synagogueId;
	
	@Column
	private String synagogueName;
	
	@Column
	private String address;
	
	@Column
	private String coordinate;
	
	@Column
	private String nosach;
	
	@OneToMany
	private List<Minyan> minyans = new ArrayList<Minyan>();
	
	
	
	public String getSynagogueId() 
	{
		return synagogueId;
	}
	
	
	public void setSynagogueId(String senagogId) 
	{
		this.synagogueId = senagogId;
	}
	
	
	public String getSynagogueName() 
	{
		return synagogueName;
	}
	
	
	public void setSynagogueName(String senagogName) 
	{
		this.synagogueName = senagogName;
	}
	
	
	public String getAddress() 
	{
		return address;
	}
	
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	
	public String getCoordinate() 
	{
		return coordinate;
	}
	
	
	public void setCoordinate(String coordinate) 
	{
		this.coordinate = coordinate;
	}
	
	
	public String getNosach() 
	{
		return nosach;
	}
	
	
	public void setNosach(String nosach) 
	{
		this.nosach = nosach;
	}
	
	
	public List<Minyan> getMinyans() 
	{
		return minyans;
	}
	
	
	public void setMinyans(List<Minyan> minyans) 
	{
		this.minyans = minyans;
	}
	
	
	public void addMinyan(Minyan minyan) 
	{
		this.minyans.add(minyan);
	}
}
