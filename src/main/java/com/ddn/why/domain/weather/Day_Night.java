package com.ddn.why.domain.weather;

//夜间天气
public class Day_Night {

	private String weather;// 天气
	private String templow;// 夜晚最低气温
	private String temphigh;// 白天最高气温
	private String img;// 图标
	private String winddirect;// 风向
	private String windpower;// 风级

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemplow() {
		return templow;
	}

	public void setTemplow(String templow) {
		this.templow = templow;
	}

	public String getTemphigh() {
		return temphigh;
	}

	public void setTemphigh(String temphigh) {
		this.temphigh = temphigh;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	@Override
	public String toString() {
		return "Day_Night [weather=" + weather + ", templow=" + templow + ", temphigh=" + temphigh + ", img=" + img + ", winddirect=" + winddirect + ", windpower=" + windpower + "]";
	}

}
