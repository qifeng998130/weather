package com.ddn.why.domain.moji;

public class AqiForecast {

	private String date;
	private String publishTime;
	private String value;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AqiForecast() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public String toString() {
		return "AqiForecast [date=" + date + ", publishTime=" + publishTime + ", value=" + value + "]";
	}

	public AqiForecast(String date, String publishTime, String value) {
		super();
		this.date = date;
		this.publishTime = publishTime;
		this.value = value;
	}
	

}
