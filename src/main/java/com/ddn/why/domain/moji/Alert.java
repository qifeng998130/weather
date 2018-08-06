package com.ddn.why.domain.moji;

//天气预警模型
public class Alert {

	private String content;// 预警内容
	private String infoid;// 预警id
	private String title;// 标题
	private String pub_time;// 发布时间
	private String level;// 等级
	private String name;// 预警名称
	private String type;// 预警类型

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPub_time() {
		return pub_time;
	}

	public void setPub_time(String pub_time) {
		this.pub_time = pub_time;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Alert [content=" + content + ", infoid=" + infoid + ", title=" + title + ", pub_time=" + pub_time + ", level=" + level + ", name=" + name + ", type=" + type + "]";
	}

}
