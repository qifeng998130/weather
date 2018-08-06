package com.ddn.why.domain.danmu;

//弹幕模型
public class Danmu {
	
	private int danmuid;// 弹幕id
	private String content;// 弹幕内容
	private String uuid;
	private long timestamp;// 时间戳

	public int getDanmuid() {
		return danmuid;
	}

	public void setDanmuid(int danmuid) {
		this.danmuid = danmuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Danmu [danmuid=" + danmuid + ", content=" + content + ", uuid=" + uuid + ", timestamp=" + timestamp + "]";
	}

}
