package com.ddn.why.domain.weather;

public class City {

	private Integer cityid;
	private String citycode;
	private String city;
	private Integer parentid;

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

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

	@Override
	public String toString() {
		return "City [cityid=" + cityid + ", parentid=" + parentid + ", citycode=" + citycode + ", city=" + city + "]";
	}
	
}
