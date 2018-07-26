package com.minyan.karov.services.zmanim;

public class TimeZoneDb {
	private String status;//": "OK",
	
	private String message;//": "",
	
	private String countryCode;//": "IL",
	
	private String countryName;//": "Israel",
	
	private String zoneName;//": "Asia/Jerusalem",
	
	private String abbreviation;//": "IDT",
	
	private double gmtOffset;//": 10800,
	
	private double dst;//": "1",
	
	private double dstStart;//": 1521763200,
	
	private double dstEnd;//": 1540681200,
	
	private String nextAbbreviation;//": "IST",
	
	private double timestamp;//": 1532567165,
	
	private String formatted;//": "2018-07-26 01:06:05"

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public double getGmtOffset() {
		return gmtOffset;
	}

	public void setGmtOffset(double gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	public double getDst() {
		return dst;
	}

	public void setDst(double dst) {
		this.dst = dst;
	}

	public double getDstStart() {
		return dstStart;
	}

	public void setDstStart(double dstStart) {
		this.dstStart = dstStart;
	}

	public double getDstEnd() {
		return dstEnd;
	}

	public void setDstEnd(double dstEnd) {
		this.dstEnd = dstEnd;
	}

	public String getNextAbbreviation() {
		return nextAbbreviation;
	}

	public void setNextAbbreviation(String nextAbbreviation) {
		this.nextAbbreviation = nextAbbreviation;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	public String getFormatted() {
		return formatted;
	}

	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}
}
