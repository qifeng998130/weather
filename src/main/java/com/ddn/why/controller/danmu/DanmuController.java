package com.ddn.why.controller.danmu;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddn.why.domain.danmu.Danmu;
import com.ddn.why.service.danmu.DanmuService;
import com.ddn.why.service.umeng.UmengService;
import com.ddn.why.service.xinge.XingeService;
import com.ddn.why.utils.SensitivewordFilter;
import com.ddn.why.utils.umeng.AndroidUnicast;
import com.tencent.xinge.XingeApp;

//弹幕处理器
@Controller
@RequestMapping("/weather")
public class DanmuController {

	@Autowired
	private DanmuService danmuService;
	@Autowired
	private XingeService xingeService;// 信鸽推送
	@Autowired
	private UmengService umengService;// 友盟推送

	// 如果是返回对象的话注意不要使用produces，可能会出错
	@RequestMapping(value = "/saveAndPush", produces = "application/json; charset=utf-8")
	@ResponseBody
	public boolean saveAndPush(String content, String uuid, String currentToken) {
		// System.out.println("弹幕内容："+content);
		content = SensitivewordFilter.replaceSensitiveWord(content, 2, "*");
		// System.out.println("过滤敏感词："+content);
		// 保存弹幕
		danmuService.saveDanmuService(content, uuid);
		
		boolean flag = false;
		try {
			// 广播，每天10次
			// AndroidBroadcast weather = new
			// AndroidBroadcast("5abc6307a40fa31dab000085","fee5c79dee7ef6aa9376c8d904189b4f");
			// 单播，列播
			AndroidUnicast unicast = new AndroidUnicast("5abc6307a40fa31dab000085", "83sstqceladbzomvbv7tkharqxxjjadd");
			//分页列播推送5分钟内打开过应用的设备
			umengService.controluMengListcastPush(unicast, content, uuid, currentToken,5);
			// 信鸽推送
			// 推送弹幕给单个app的所有安卓设备
			// XingeApp xingeCalendar = new XingeApp(2100277645,"0bba2fcfabf4aa5780e3ef004a540567");//玛雅日历
			// xingeService.pushAllDeviceService(xingeCalendar, content, uuid);
			XingeApp xingeWeather = new XingeApp(2100277644, "d2410c118ba4b898a749ae9b565db119");// 玛雅天气
			xingeService.pushAllDeviceService(xingeWeather, content, uuid);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取时间内的全部弹幕并保存友盟推送的设备token
	 * @param currentToken	当前设备token
	 * @return
	 */
	@RequestMapping("/getDanmuList")
	@ResponseBody
	public List<Danmu> getDanmuList(String currentToken) {
		// System.out.println("-------------------------------");

		return danmuService.controlDanmuListService(24,currentToken);
	}

	@RequestMapping("/saveCurrentToken")
	@ResponseBody
	public boolean saveCurrentToken(String currentToken) {
		// System.out.println("-------------------------------");
		umengService.saveCurrentToken(currentToken);
		return true;
	}

	@Test
	public void text() {
		XingeApp xingeApp = new XingeApp(2100277645, "0bba2fcfabf4aa5780e3ef004a540567");
		// 查询设备数量
		System.out.println(xingeApp.queryDeviceCount());
		System.out.println(UUID.randomUUID().toString());
	}

}
