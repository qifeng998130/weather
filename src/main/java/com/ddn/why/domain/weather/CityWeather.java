package com.ddn.why.domain.weather;

public class CityWeather {

	private String citycode;
	private String city;
	private String weather;
	private String createtime;
	private String updatetime;

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "CityWeather [citycode=" + citycode + ", city=" + city + ", weather=" + weather + ", createtime=" + createtime + ", updatetime=" + updatetime + "]";
	}
	

}
