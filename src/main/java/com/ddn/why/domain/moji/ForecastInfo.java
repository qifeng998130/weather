package com.ddn.why.domain.moji;

//预报信息模型
public class ForecastInfo {

	private String cityId;//
	private String city_name;// 北京
	private String current_date_time;// 时间戳


	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCurrent_date_time() {
		return current_date_time;
	}

	public void setCurrent_date_time(String current_date_time) {
		this.current_date_time = current_date_time;
	}

	public ForecastInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForecastInfo(String cityId, String city_name, String current_date_time) {
		super();
		this.cityId = cityId;
		this.city_name = city_name;
		this.current_date_time = current_date_time;
	}
	
	
}
