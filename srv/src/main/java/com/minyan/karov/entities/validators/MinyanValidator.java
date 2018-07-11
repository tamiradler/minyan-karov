package com.minyan.karov.entities.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minyan.karov.entities.Minyan;
import com.minyan.karov.services.PutError;
import com.minyan.karov.services.StringConfigurationService;


@Component
public class MinyanValidator {
	
	
	@Autowired
	private StringConfigurationService stringConfigurationService;
	
	
	public void validate(Minyan minyan, PutError putError) {
		String minynId = minyan.getMinyanId();
		try {
			if (!stringConfigurationService.isValueContain(minyan.getIsDakotLifney(), "beforeAfter", "code")) {
				putError.putError(minynId, "minyan_DakotLifneyAcharey_error_value");
			}
			if (!stringConfigurationService.isValueContain(minyan.getDayAtWeek(), "timeAtWeek", "code")) {
				putError.putError(minynId, "minyan_DayAtWeek_error_value");
			}
			if (!stringConfigurationService.isValueContain(minyan.getTimeType(), "timeTypes", "code")) {
				putError.putError(minynId, "minyan_TimeTypes_error_value");
			}
			if ("fixed_hour".equals(minyan.getTimeType()))
			{
				if (!validateTime(minyan.getTime())) {
					putError.putError(minynId, "minyan_Time_error_value");
				}
				
			} else if ("fixed_time".equals(minyan.getTimeType())) {
				if (!validateDakot(minyan.getDakotLifneyAcharey())) {
					putError.putError(minynId, "minyan_DakotLifneyAcharey_error_value");
				}
				if (!stringConfigurationService.isValueContain(minyan.getIsDakotLifney(), "beforeAfter", "code")) {
					putError.putError(minynId, "minyan_IsDakotLifney_error_value");
				}
				if (!stringConfigurationService.isValueContain(minyan.getDayTime(), "dayTimes", "code")) {
					putError.putError(minynId, "minyan_DayTime_error_value");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			putError.putError(minynId, e.getMessage() + e.getStackTrace());
		}
	}
	
	
	private boolean validateDakot(String dakotLifneyAcharey) {
		try {
			int minutes = Integer.parseInt(dakotLifneyAcharey);
			if (minutes < 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	private boolean validateTime(String time) {
		try {
			if (time == null) {
				return false;
			}
			String [] strArr = time.split(":");
			if (strArr.length != 2) {
				return false;
			}
			if (strArr[0].length() != 2 || strArr[1].length() != 2) {
				return false;
			}
			if (!isInteger(strArr[0]) || !isInteger(strArr[0])) {
				return false;
			}
			
			int hour = Integer.parseInt(strArr[0]);
			int minutes = Integer.parseInt(strArr[1]);
			
			if (hour > 23 || hour < -1) {
				return false;
			}
			
			if (minutes > 59 || minutes < -1) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	

}
