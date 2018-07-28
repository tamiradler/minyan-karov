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
}

