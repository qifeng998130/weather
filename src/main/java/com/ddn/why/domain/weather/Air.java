package com.ddn.why.domain.weather;

//空气质量模型
public class Air {

	private String quality;// 空气质量,优
	private String aqi;// 空气质量指数
	private String pm10;// PM 10一小时平均量
	private String pm2_5;// PM 2.5一小时平均量
	private String so2;// 二氧化硫一小时平均量
	private String no2;// 二氧化氮一小时平均量
	private String co;// 一氧化碳一小时平均量，mg/m3
	private String o3;// 臭氧一小时平均量
	private String level;// 等级
	private String color;// 指数颜色值
	private String update_time;// 更新时间戳

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getAqi() {
		return aqi;
	}

	public void setAqi(String aqi) {
		this.aqi = aqi;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getPm2_5() {
		return pm2_5;
	}

	public void setPm2_5(String pm2_5) {
		this.pm2_5 = pm2_5;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "Air [quality=" + quality + ", aqi=" + aqi + ", pm10=" + pm10 + ", pm2_5=" + pm2_5 + ", so2=" + so2 + ", no2=" + no2 + ", co=" + co + ", o3=" + o3 + ", level=" + level + ", color=" + color + ", update_time=" + update_time + "]";
	}

	public Air() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Air(String quality, String aqi, String pm10, String pm2_5, String so2, String no2, String co, String o3, String level, String color, String update_time) {
		super();
		this.quality = quality;
		this.aqi = aqi;
		this.pm10 = pm10;
		this.pm2_5 = pm2_5;
		this.so2 = so2;
		this.no2 = no2;
		this.co = co;
		this.o3 = o3;
		this.level = level;
		this.color = color;
		this.update_time = update_time;
	}

}
