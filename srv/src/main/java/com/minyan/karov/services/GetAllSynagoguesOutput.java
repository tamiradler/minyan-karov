package com.minyan.karov.services;

import java.util.ArrayList;
import java.util.List;

import com.minyan.karov.entities.Synagogue;

public class GetAllSynagoguesOutput 
{
	private List<Synagogue> synagogues = new ArrayList<>();

	public List<Synagogue> getSynagogues() {
		return synagogues;
	}

	public void setSynagogues(List<Synagogue> synagogues) {
		this.synagogues = synagogues;
	}
	
	public void addSynagogue(Synagogue synagogue) {
		synagogues.add(synagogue);
	}
}
