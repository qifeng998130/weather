package com.ddn.why.domain.weather;

import java.util.List;

//当前的天气情况
public class CurrentConditions {
	private String week;// 星期
	private String weather;// 天气
	private String tempurature_c;
	private String tempurature;
	private String temp;// 气温
	private String temphigh;// 最高气温
	private String templow;// 最低气温
	private String icon;// 图标
	private String humidity;// 湿度
	private String pressure;// 气压
	private String windspeed;// 风速
	private String winddirect;// 风向
	private String windpower;// 风级
	private String update_time;// 更新时间戳

	private List<Index> index;// 生活指数
	private Air air;// 空气质量指数

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTempurature_c() {
		return tempurature_c;
	}

	public void setTempurature_c(String tempurature_c) {
		this.tempurature_c = tempurature_c;
	}

	public String getTempurature() {
		return tempurature;
	}

	public void setTempurature(String tempurature) {
		this.tempurature = tempurature;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTemphigh() {
		return temphigh;
	}

	public void setTemphigh(String temphigh) {
		this.temphigh = temphigh;
	}

	public String getTemplow() {
		return templow;
	}

	public void setTemplow(String templow) {
		this.templow = templow;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(String windspeed) {
		this.windspeed = windspeed;
	}

	public String getWinddirect() {
		return winddirect;
	}

	public void setWinddirect(String winddirect) {
		this.winddirect = winddirect;
	}

	public String getWindpower() {
		return windpower;
	}

	public void setWindpower(String windpower) {
		this.windpower = windpower;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public List<Index> getIndex() {
		return index;
	}

	public void setIndex(List<Index> index) {
		this.index = index;
	}

	public Air getAir() {
		return air;
	}

	public void setAir(Air air) {
		this.air = air;
	}

	public CurrentConditions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurrentConditions(String week, String weather, String tempurature_c, String tempurature, String temp, String temphigh, String templow, String icon, String humidity, String pressure, String windspeed, String winddirect, String windpower, String update_time, List<Index> index, Air air) {
		super();
		this.week = week;
		this.weather = weather;
		this.tempurature_c = tempurature_c;
		this.tempurature = tempurature;
		this.temp = temp;
		this.temphigh = temphigh;
		this.templow = templow;
		this.icon = icon;
		this.humidity = humidity;
		this.pressure = pressure;
		this.windspeed = windspeed;
		this.winddirect = winddirect;
		this.windpower = windpower;
		this.update_time = update_time;
		this.index = index;
		this.air = air;
	}

}
