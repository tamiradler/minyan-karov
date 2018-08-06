package com.minyan.karov.services.zmanim;

import java.util.Calendar;

public class Zmanim {

    private double day = 10.0;
    private double month = 5.0;
    private double year = 2016.0;

    private double latitude = 32.077735;
    private double longitude = 34.785443;
    
    double gmtOffset = 0;
    
    public void setGmtOffset(double gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	/**
     * The method set the date.
     */
    public void setDate(double iYear, double iMonth, double iDay) {
        day = iDay;
        month = iMonth;
        year = iYear;
    }

    /**
     * The method set the latitude and the longitude.
     */
    public void setLatLng(String latLng) {
        setLatLng(latLng.split(","));
    }

    /**
     * The method set the latitude and the longitude.
     */
    public void setLatLng(String [] latLng) {
        setLatLng(latLng[0], latLng[1]);
    }

    /**
     * The method set the latitude and the longitude.
     */
    public void setLatLng(String iLatitude, String iLongitude) {
        setLatLng(Double.parseDouble(iLatitude), Double.parseDouble(iLongitude));
    }

    /**
     * The method set the latitude and the longitude.
     */
    public void setLatLng(double iLatitude, double iLongitude) {
        latitude = iLatitude;
        longitude = iLongitude;
    }

    public String getNoon() {
        double day = computeDay();
        double sunrise = computeSunrise(day, true);
        double sunset = computeSunrise(day, false);

        double noon = (sunset + sunrise)/2;
        return secoundsToTime(noon);
    }

    
    /**
     * 
     * @return
     */
    public double getShaaZmanit() {
    	double day = computeDay();
        double sunrise = computeSunrise(day, true);
        double sunset = computeSunrise(day, false);
        return (sunset-sunrise)/12.0;
    }
    
    
    /**
     * 
     * @return
     */
    public String getShaaZmanitTime() {
    	double day = computeDay();
        double sunrise = computeSunrise(day, true);
        double sunset = computeSunrise(day, false);
        return String.valueOf((sunset-sunrise)/12.0/60/60);
    }
    
    
    
    /**
     * 
     * @return
     */
    public double getKnisatShabat() {
    	double day = computeDay();
        double sunset = computeSunrise(day, false);
        double tosefetShabat = 60.0*21.0;
        String latLng = String.valueOf(latitude) + "," + String.valueOf(longitude);
        if (contains(latLng, HAIFA_LAT_LNG)) {
        	tosefetShabat = 60.0*30.0;
        } else if (contains(latLng, JERUSALEM_LAT_LNG)) {
        	tosefetShabat = 60.0*40.0;
        }
        return sunset - tosefetShabat;
    }
    
    
    /**
     * 
     * @return
     */
    public String getKnisatShabatTime() {
        return secoundsToTime(getKnisatShabat());
    }
    
    
    public String getAlotHashahar()
    {
    	double day = computeDay();
        double sunrise = computeSunrise(day, true);
        return secoundsToTime(sunrise - 60.0*72.0);
    }
    

    /**
     * 
     * @return
     */
    public String getTzetHkohavim()
    {
    	double day = computeDay();
        double sunset = computeSunrise(day, false);
        double shaaZmanit = getShaaZmanit();
        double dakaZmanit = shaaZmanit/60;
        
        double tzetHkohavim = sunset;
        
        if (dakaZmanit < 60.0)
        {
        	tzetHkohavim += 60.0*18.0; 
        }
        else
        {
        	tzetHkohavim += dakaZmanit*18.0; 
        }
        return secoundsToTime(tzetHkohavim);
    }
    
    
    /**
     * The method return the sunrise time in HH:MM:SS format.
     *
     * @return
     */
    public String getSunriseTime() {
        double day = computeDay();
        double sec = computeSunrise(day, true);
        return secoundsToTime(sec);
    }

    public String getSunsetTime() {
        double day = computeDay();
        double sec = computeSunrise(day, false);
        return secoundsToTime(sec);
    }

    /**
     * The method get time in secound from 00:00 and return the time in format HH:MM:SS.
     *
     * @param secounds
     * @return
     */
    public String secoundsToTime(double secounds) {
        int h = (int) (secounds/(60*60));
        secounds -= h*(60*60);
        int m = (int) (secounds/60);
        secounds -= m*60;

        String min = "";
        if (m < 10) {
            min = "0" + Integer.toString(m);
        }
        else {
            min = Integer.toString(m);
        }

        String sec = "";
        if (secounds < 10) {
            sec = "0" + Integer.toString((int)secounds);
        }
        else {
            sec = Integer.toString((int)secounds);
        }

        String time = Integer.toString(h) + ":" + min + ":" + sec;
        return time;
    }


    /**
     * The method return the number of the day in the year.
     *
     * @return
     */
    public double computeDay() {
        double N1 = Math.floor(275.0 * getMonth() / 9.0);
        double N2 = Math.floor((getMonth() + 9.0) / 12.0);
        double N3 = (1 + Math.floor((getYear() - 4.0 * Math.floor(getYear() / 4.0) + 2.0) / 3.0));
        double N = N1 - (N2 * N3) + getDay() - 30.0;

        return N;
    }

    /**
     * The method will return the time of sunrise if sunrise = true, else will return sunset.
     * The return vslue is in secound after 00:00.
     *
     * @param day
     * @param sunrise
     * @return
     */
    public double computeSunrise(double day, boolean sunrise)
    {
        double zenith = 90.83333333333333;
        double D2R = Math.PI / 180.0;
        double R2D = 180.0 / Math.PI;

        // convert the longitude to hour value and calculate an approximate time
        double lnHour = longitude / 15.0;
        double t;
        if (sunrise) {
            t = day + ((6.0 - lnHour) / 24.0);
        } else {
            t = day + ((18.0 - lnHour) / 24.0);
        }

        //calculate the Sun's mean anomaly
        double M = (0.9856 * t) - 3.289;


        //calculate the Sun's true longitude
        double L = M + (1.916 * Math.sin(M * D2R)) + (0.020 * Math.sin(2.0 * M * D2R)) + 282.634;
        if (L > 360.0) {
            L = L - 360.0;
        } else if (L < 0.0) {
            L = L + 360.0;
        }


        //calculate the Sun's right ascension
        double RA = R2D * Math.atan(0.91764 * Math.tan(L * D2R));
        if (RA > 360.0) {
            RA = RA - 360.0;
        } else if (RA < 0.0) {
            RA = RA + 360.0;
        }


        //right ascension value needs to be in the same qua
        double Lquadrant = (Math.floor(L / (90.0))) * 90.0;
        double RAquadrant = (Math.floor(RA / 90.0)) * 90.0;
        RA = RA + (Lquadrant - RAquadrant);


        //right ascension value needs to be converted into hours
        RA = RA / 15.0;

        //calculate the Sun's declination
        double sinDec = 0.39782 * Math.sin(L * D2R);
        double cosDec = Math.cos(Math.asin(sinDec));

        //calculate the Sun's local hour angle
        double cosH = (Math.cos(zenith * D2R) - (sinDec * Math.sin(latitude * D2R))) / (cosDec * Math.cos(latitude * D2R));
        double H;
        if (sunrise) {
            H = 360.0 - R2D * Math.acos(cosH);
        } else {
            H = R2D * Math.acos(cosH);
        }
        H = H / 15.0;


        //calculate local mean time of rising/setting
        double T = H + RA - (0.06571 * t) - 6.622;

        //adjust back to UTC
        double UT = T - lnHour;
        if (UT > 24.0) {
            UT = UT - 24.0;
        } else if (UT < 0.0) {
            UT = UT + 24.0;
        }

        //convert UT value to local time zone of latitude/longitude
        double localT = UT + gmtOffset;

        //convert to Milliseconds
        return localT * 3600.0;// * 1000;
    }


    /**
     * The method will return the current date as DD/MM/YYYY format.
     *
     * @return
     */
    public String getTodayDateAsString() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        return getDateAsString(year, month, day);
    }

    public static String getDateAsString(int year, int monthOfYear, int dayOfMonth) {
        return Integer.toString(dayOfMonth) + "/" + Integer.toString(monthOfYear) + "/" + Integer.toString(year);
    }

    public double getDay() {
        return day;
    }

    public double getMonth() {
        return month;
    }

    public double getYear() {
        return year;
    }
    
    
    
    
    /**
     * Return true if the given point is contained inside the boundary.
     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     * @param test The point to check
     * @return true if the point is inside the boundary, false otherwise
     *
     */
    public boolean contains(String latLng, String [] points) {
    	int i;
    	int j;
    	boolean result = false;
	    for (i = 0, j = points.length - 1; i < points.length; j = i++) {
	        if ((Double.parseDouble(points[i].split(",")[1]) 
	        				> 
	        		Double.parseDouble(latLng.split(",")[1])
	        				) != (
	        						Double.parseDouble(points[j].split(",")[1])
	        								> 
	        						Double.parseDouble(latLng.split(",")[1])
	        								) &&
	            (
	            		Double.parseDouble(latLng.split(",")[0]) 
	            		< (Double.parseDouble(points[j].split(",")[0]) 
	            				- Double.parseDouble(points[i].split(",")[0])) 
	            		* (Double.parseDouble(latLng.split(",")[1])-Double.parseDouble(points[i].split(",")[1])) / (Double.parseDouble(points[j].split(",")[1])-Double.parseDouble(points[i].split(",")[1])) + 
	            				Double.parseDouble(points[i].split(",")[0]))) 
	        {
	        	result = !result;
	        }
      	}
      
      
      	return result;
	}
    
    
    public static final String [] HAIFA_LAT_LNG = {"32.837499,34.980379",
    		"32.827164,34.954101",
    		"32.824642,34.952667",
    		"32.784712,34.950542",
    		"32.756175,34.990149",
    		"32.755957,35.025424",
    		"32.788867,35.080702",
    		"32.843623,35.082072"};
    
    public static final String [] JERUSALEM_LAT_LNG = {
		    "31.846793,35.241139",
		    "31.826828,35.180927",
		    "31.794187,35.174316",
		    "31.785157,35.153497",
		    "31.775301,35.147856",
		    "31.740835,35.165761",
		    "31.724059,35.187730",
		    "31.727105,35.209577",
		    "31.717297,35.218681",
		    "31.722768,35.234582",
		    "31.821753,35.281044",
		    "31.848733,35.245594"};
}

