package com.ddn.why.domain.moji;

public class Index {

	private String day;//日期
	private String name;// 指数名称 :name
	private String status;// 指数值:status
	private String desc;// 指数详情:desc

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Index [day=" + day + ", name=" + name + ", status=" + status + ", desc=" + desc + "]";
	}
	
}
