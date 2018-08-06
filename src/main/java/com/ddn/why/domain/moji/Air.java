package com.ddn.why.domain.moji;

public class Air {

	private String cityName;
	private String pubtime;
	private String value;
	private String co;
	private String no2;
	private String o3;
	private String pm10;
	private String pm25;
	private String rank;// 排名
	private String so2;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public Air() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Air(String cityName, String pubtime, String value, String co, String no2, String o3, String pm10, String pm25, String rank, String so2) {
		super();
		this.cityName = cityName;
		this.pubtime = pubtime;
		this.value = value;
		this.co = co;
		this.no2 = no2;
		this.o3 = o3;
		this.pm10 = pm10;
		this.pm25 = pm25;
		this.rank = rank;
		this.so2 = so2;
	}
	

}
