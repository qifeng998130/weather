package com.ddn.why.domain.moji;

import java.util.List;

public class Condition {

	private String weather;// 天气
	private String temp;// 气温
	private String icon;// 图标
	private String humidity;// 湿度
	private String pressure;// 气压
	private String windspeed;// 风速
	private String winddirect;// 风向
	private String windpower;// 风级
	private String update_time;// 更新时间戳
	
	private String conditionId;//实时天气id
	private String realFeel;//体感温度
	private String tips;
	private String uvi;//紫外线
	
	private List<Index> indexs;
	private Air air;

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getRealFeel() {
		return realFeel;
	}

	public void setRealFeel(String realFeel) {
		this.realFeel = realFeel;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getUvi() {
		return uvi;
	}

	public void setUvi(String uvi) {
		this.uvi = uvi;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
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
	
	public List<Index> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<Index> indexs) {
		this.indexs = indexs;
	}

	public Air getAir() {
		return air;
	}

	public void setAir(Air air) {
		this.air = air;
	}

	public Condition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Condition(String weather, String temp, String icon, String humidity, String pressure, String windspeed, String winddirect, String windpower, String update_time, String conditionId, String realFeel, String tips, String uvi, List<Index> indexs, Air air) {
		super();
		this.weather = weather;
		this.temp = temp;
		this.icon = icon;
		this.humidity = humidity;
		this.pressure = pressure;
		this.windspeed = windspeed;
		this.winddirect = winddirect;
		this.windpower = windpower;
		this.update_time = update_time;
		this.conditionId = conditionId;
		this.realFeel = realFeel;
		this.tips = tips;
		this.uvi = uvi;
		this.indexs = indexs;
		this.air = air;
	}

	//没有生活指数和空气质量指数
	public Condition(String weather, String temp, String icon, String humidity, String pressure, String windspeed, String winddirect, String windpower, String update_time, String conditionId, String realFeel, String tips, String uvi) {
		super();
		this.weather = weather;
		this.temp = temp;
		this.icon = icon;
		this.humidity = humidity;
		this.pressure = pressure;
		this.windspeed = windspeed;
		this.winddirect = winddirect;
		this.windpower = windpower;
		this.update_time = update_time;
		this.conditionId = conditionId;
		this.realFeel = realFeel;
		this.tips = tips;
		this.uvi = uvi;
	}
	
	

}
