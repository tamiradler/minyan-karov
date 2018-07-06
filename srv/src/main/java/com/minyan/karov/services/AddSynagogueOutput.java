package com.minyan.karov.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSynagogueOutput implements PutError {
	private Map <String, List<String>> errors = new HashMap<>();

	@Override
	public void putError(String elmId, String error) {
		List<String> errorsList =  errors.get(elmId);
		if (errorsList == null) {
			errorsList = new ArrayList<>();
			errors.put(elmId, errorsList);
		}
		errorsList.add(error);
	}
	
	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<String>> errors) {
		this.errors = errors;
	}
}
