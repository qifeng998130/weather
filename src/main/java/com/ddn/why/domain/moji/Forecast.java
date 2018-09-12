package com.ddn.why.domain.moji;

public class Forecast {

	private String date;// 日期
	private String updatetime;// 更新时间

	private String conditionDay;// 天气
	private String imgDay;
	private String sunrise;// 日出
	private String sunset;// 日落
	private String tempDay;// 白天气温
	private String winddirectDay;// 风向
	private String windpowerDay;// 风级
	private String windspeedDay;// 风速

	private String conditionNight;
	private String imgNight;// 夜间图标
	private String moonphase;// 月相
	private String moonrise;// 月出
	private String moonset;// 月落
	private String tempNight;
	private String winddirectNight;// 风向
	private String windpowerNight;// 风级
	private String windspeedNight;// 风速

	private AqiForecast aqiForecast;
	private CaiyunForecastData cydressing;
	private CaiyunForecastData cycomfrot;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getConditionDay() {
		return conditionDay;
	}

	public void setConditionDay(String conditionDay) {
		this.conditionDay = conditionDay;
	}

	public String getImgDay() {
		return imgDay;
	}

	public void setImgDay(String imgDay) {
		this.imgDay = imgDay;
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

	public String getTempDay() {
		return tempDay;
	}

	public void setTempDay(String tempDay) {
		this.tempDay = tempDay;
	}

	public String getWinddirectDay() {
		return winddirectDay;
	}

	public void setWinddirectDay(String winddirectDay) {
		this.winddirectDay = winddirectDay;
	}

	public String getWindpowerDay() {
		return windpowerDay;
	}

	public void setWindpowerDay(String windpowerDay) {
		this.windpowerDay = windpowerDay;
	}

	public String getWindspeedDay() {
		return windspeedDay;
	}

	public void setWindspeedDay(String windspeedDay) {
		this.windspeedDay = windspeedDay;
	}

	public String getConditionNight() {
		return conditionNight;
	}

	public void setConditionNight(String conditionNight) {
		this.conditionNight = conditionNight;
	}

	public String getImgNight() {
		return imgNight;
	}

	public void setImgNight(String imgNight) {
		this.imgNight = imgNight;
	}

	public String getMoonphase() {
		return moonphase;
	}

	public void setMoonphase(String moonphase) {
		this.moonphase = moonphase;
	}

	public String getMoonrise() {
		return moonrise;
	}

	public void setMoonrise(String moonrise) {
		this.moonrise = moonrise;
	}

	public String getMoonset() {
		return moonset;
	}

	public void setMoonset(String moonset) {
		this.moonset = moonset;
	}

	public String getTempNight() {
		return tempNight;
	}

	public void setTempNight(String tempNight) {
		this.tempNight = tempNight;
	}

	public String getWinddirectNight() {
		return winddirectNight;
	}

	public void setWinddirectNight(String winddirectNight) {
		this.winddirectNight = winddirectNight;
	}

	public String getWindpowerNight() {
		return windpowerNight;
	}

	public void setWindpowerNight(String windpowerNight) {
		this.windpowerNight = windpowerNight;
	}

	public String getWindspeedNight() {
		return windspeedNight;
	}

	public void setWindspeedNight(String windspeedNight) {
		this.windspeedNight = windspeedNight;
	}

	public AqiForecast getAqiForecast() {
		return aqiForecast;
	}

	public void setAqiForecast(AqiForecast aqiForecast) {
		this.aqiForecast = aqiForecast;
	}


	public CaiyunForecastData getCydressing() { return cydressing; }

	public void setCydressing(CaiyunForecastData cydressing) {
		this.cydressing = cydressing;
	}


	public CaiyunForecastData getCycomfrot() { return cycomfrot; }

	public void setCycomfrot(CaiyunForecastData cycomfrot) {
		this.cycomfrot = cycomfrot;
	}


	public Forecast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Forecast(String date, String updatetime, String conditionDay, String imgDay, String sunrise, String sunset, String tempDay,
					String winddirectDay, String windpowerDay, String windspeedDay, String conditionNight, String imgNight,
					String moonphase, String moonrise, String moonset, String tempNight, String winddirectNight,
					String windpowerNight, String windspeedNight, AqiForecast aqiForecast, CaiyunForecastData daily_dressing_forecast,CaiyunForecastData daily_carWashing_forecast) {
		super();
		this.date = date;
		this.updatetime = updatetime;
		this.conditionDay = conditionDay;
		this.imgDay = imgDay;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.tempDay = tempDay;
		this.winddirectDay = winddirectDay;
		this.windpowerDay = windpowerDay;
		this.windspeedDay = windspeedDay;
		this.conditionNight = conditionNight;
		this.imgNight = imgNight;
		this.moonphase = moonphase;
		this.moonrise = moonrise;
		this.moonset = moonset;
		this.tempNight = tempNight;
		this.winddirectNight = winddirectNight;
		this.windpowerNight = windpowerNight;
		this.windspeedNight = windspeedNight;
		this.aqiForecast = aqiForecast;
		if(daily_dressing_forecast != null && daily_carWashing_forecast!=null){

			this.cydressing = daily_dressing_forecast;
			this.cycomfrot = daily_carWashing_forecast;
		}
	}

	@Override
	public String toString() {
		return "Forecast [date=" + date + ", updatetime=" + updatetime + ", conditionDay=" + conditionDay + ", imgDay=" + imgDay + ", sunrise=" + sunrise + ", sunset=" + sunset + ", tempDay=" + tempDay + ", winddirectDay=" + winddirectDay + ", windpowerDay=" + windpowerDay + ", windspeedDay=" + windspeedDay + ", conditionNight=" + conditionNight + ", imgNight=" + imgNight + ", moonphase=" + moonphase + ", moonrise=" + moonrise + ", moonset=" + moonset + ", tempNight=" + tempNight + ", winddirectNight=" + winddirectNight + ", windpowerNight=" + windpowerNight + ", windspeedNight=" + windspeedNight + ", aqiForecast=" + aqiForecast + "]";
	}

}
