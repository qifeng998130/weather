package com.ddn.why.domain.weather;

//24小时天气模型
public class Hourly {

	private String time;// 时间
	private String weather;// 天气
	private String temp;// 气温
	private String img;// 图标

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
