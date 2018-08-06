package com.ddn.why.service.xinge;

import org.json.JSONObject;

import com.tencent.xinge.XingeApp;

//信鸽推送业务接口
public interface XingeService {

	/**
	 * 推送弹幕到所有 安卓 设备
	 * deviceType 为0
	 * @param 类供与信鸽后台交互的接口 两个参数：accessId ：推送目标应用id;secretKey :推送密钥 
	 * @param danmu danmu内容
	 * @return
	 */
	JSONObject pushAllDeviceService(XingeApp xingeApp,String content,String uuid);
	
}
