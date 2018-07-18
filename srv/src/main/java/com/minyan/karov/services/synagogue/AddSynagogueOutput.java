package com.minyan.karov.services.synagogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minyan.karov.services.PutError;
import com.minyan.karov.services.ServiceOutput;

public class AddSynagogueOutput extends ServiceOutput implements PutError {
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
