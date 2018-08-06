package com.ddn.why.service.danmu;

import java.util.List;

import com.ddn.why.domain.danmu.Danmu;

//弹幕本地操作接口
public interface DanmuService {

	/**
	 * 保存弹幕内容
	 * @param content 内容
	 */
	void saveDanmuService(String content,String uuid);
	
	/**
	 * 查询时间内的所有弹幕
	 * @param hour 小时
	 * @return
	 */
	List<Danmu> controlDanmuListService(int hour,String currentToken);
	
}
