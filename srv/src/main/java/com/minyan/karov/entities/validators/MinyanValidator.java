package com.minyan.karov.entities.validators;
import com.minyan.karov.entities.Minyan;
import com.minyan.karov.services.PutError;


public class MinyanValidator {
	
	public void validate(Minyan minyan, PutError putError) {
		String minynId = minyan.getMinyanId();
		if (minyan.getDakotLifneyAcharey() == null || minyan.getDakotLifneyAcharey().trim().equals("")) {
			putError.putError(minynId, "minyan_DakotLifneyAcharey_empty");
		}
	}
	

}
