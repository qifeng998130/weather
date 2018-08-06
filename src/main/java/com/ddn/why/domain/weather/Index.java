package com.ddn.why.domain.weather;

//生活指数模型
public class Index {

	private String iname;// 指数名称
	private String ivalue;// 指数值
	private String detail;// 指数详情

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public String getIvalue() {
		return ivalue;
	}

	public void setIvalue(String ivalue) {
		this.ivalue = ivalue;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Index [iname=" + iname + ", ivalue=" + ivalue + ", detail=" + detail + "]";
	}

}
