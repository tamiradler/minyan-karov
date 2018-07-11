package com.minyan.karov.entities.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minyan.karov.entities.Minyan;
import com.minyan.karov.entities.Synagogue;
import com.minyan.karov.services.PutError;

@Component
public class SynagogueValidator {

	@Autowired
	private MinyanValidator minyanValidator;
	
	
	
	public void validate(Synagogue synagogue, PutError putError) {
		if (synagogue.getAddress() == null || synagogue.getAddress().trim().equals("")) {
			putError.putError("synagogue", "synagogue_address_empty");
		}
		
		if (synagogue.getNosach() == null || synagogue.getNosach().trim().equals("")) {
			putError.putError("synagogue", "synagogue_nosach_empty");
		}
		
		if (synagogue.getSynagogueName() == null || synagogue.getSynagogueName().trim().equals("")) {
			putError.putError("synagogue", "synagogue_name_empty");
		}
		
		if (synagogue.getCoordinate() == null || synagogue.getCoordinate().trim().equals("")) {
			putError.putError("synagogue", "synagogue_coordinate_empty");
		}
		
		if (synagogue.getMinyans() == null) {
			return;
		}
		
		for (Minyan minyan : synagogue.getMinyans())
		{
			minyanValidator.validate(minyan, putError);
		}
	}

}
