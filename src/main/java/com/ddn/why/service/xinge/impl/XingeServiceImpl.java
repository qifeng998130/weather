package com.ddn.why.service.xinge.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ddn.why.service.xinge.XingeService;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

@Service
public class XingeServiceImpl implements XingeService {

	//推送消息给单个app的所有安卓设备
	@Override
	public JSONObject pushAllDeviceService(XingeApp xingeApp, String content,String uuid) {
		//
		Message message = new Message();
		message.setType(Message.TYPE_MESSAGE);//透传消息
		//用户透传消息
		Map<String,Object> custom = new HashMap<String,Object>();
		custom.put("content",content);
		custom.put("uuid",uuid);
		message.setCustom(custom);
		//按accessid分发消息
		message.setMultiPkg(1);
		return xingeApp.pushAllDevice(0, message);
	}

}
