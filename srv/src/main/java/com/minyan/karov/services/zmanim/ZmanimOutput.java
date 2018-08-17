package com.minyan.karov.services.zmanim;

public class ZmanimOutput 
{
	private String noon;

	private String sunrise;
	
	private String sunset;
	
	private String shaaZmanit;
	
	private String tzetHkohavim;
	
	private String alotHashahar;
	
	private String knisatShabat;
	
	private String tzetShabat;
	
	public String getTzetShabat() {
		return tzetShabat;
	}

	public void setTzetShabat(String tzetShabat) {
		this.tzetShabat = tzetShabat;
	}

	public String getKnisatShabat() {
		return knisatShabat;
	}

	public void setKnisatShabat(String knisatShabat) {
		this.knisatShabat = knisatShabat;
	}

	public String getAlotHashahar() {
		return alotHashahar;
	}

	public void setAlotHashahar(String alotHashahar) {
		this.alotHashahar = alotHashahar;
	}

	public String getTzetHkohavim() {
		return tzetHkohavim;
	}

	public void setTzetHkohavim(String tzetHkohavim) {
		this.tzetHkohavim = tzetHkohavim;
	}

	public String getShaaZmanit() {
		return shaaZmanit;
	}

	public void setShaaZmanit(String shaaZmanit) {
		if (shaaZmanit == null) {
			return;
		}
		
		if (shaaZmanit.length() > 5){
			this.shaaZmanit = shaaZmanit.substring(0, 5);
		} else {
			this.shaaZmanit = shaaZmanit;
		}
	}

	public String getNoon() {
		return noon;
	}

	public void setNoon(String noon) {
		this.noon = noon;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
}
