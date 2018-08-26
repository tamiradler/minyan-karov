package com.minyan.karov.services.synagogue;

import com.minyan.karov.entities.Synagogue;

public class AddSynagogueInput {
	private Synagogue synagogue;
	
	private String tokenId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Synagogue getSynagogue() {
		return synagogue;
	}

	public void setSynagogue(Synagogue synagogue) {
		this.synagogue = synagogue;
	}
	
}
