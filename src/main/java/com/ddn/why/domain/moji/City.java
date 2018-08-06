package com.ddn.why.domain.moji;

public class City {

	private String cityId;
	private String name;
	private String province;// 省
	private String location;// 经纬度

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", name=" + name + ", province=" + province +  ", location=" + location + "]";
	}

}
