package com.ddn.why.domain.weather;

//未来一周天气模型
public class ForecastCondi {

	private String date;// 日期
	private String week;// 星期
	private String sunrise;// 日出
	private String sunset;// 日落

	private Day_Night day;// 夜间和白天天气
	private Day_Night night;// 夜间和白天天气

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

	public Day_Night getDay() {
		return day;
	}

	public void setDay(Day_Night day) {
		this.day = day;
	}

	public Day_Night getNight() {
		return night;
	}

	public void setNight(Day_Night night) {
		this.night = night;
	}

	@Override
	public String toString() {
		return "ForecastCondi [date=" + date + ", week=" + week + ", sunrise=" + sunrise + ", sunset=" + sunset + ", day=" + day + ", night=" + night + "]";
	}

}
