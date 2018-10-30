package com.ddn.why.service.umeng.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ddn.why.mapper.danmu.DanmuMapper;
import com.ddn.why.service.umeng.UmengService;
import com.ddn.why.utils.umeng.AndroidBroadcast;
import com.ddn.why.utils.umeng.AndroidNotification.DisplayType;
import com.ddn.why.utils.umeng.AndroidUnicast;
import com.ddn.why.utils.umeng.PushClient;

@Service
public class UmengServiceImpl implements UmengService {

	@Autowired
	private DanmuMapper danmuMapper;

	private PushClient client = new PushClient();

	private AndroidUnicast unicast = null;

	@Override
	public boolean saveCurrentToken(String currentToken){
		// 判断当前token是否为空，不为空再查询保存--这里主要是更新设备的活跃时间
		if (!StringUtils.isEmpty(currentToken)) {
			// 查询设备信息，判断是否已经存在
			Integer deviceId = danmuMapper.selectDeviceToken(currentToken);
			//当前时间戳
			long timestamp = System.currentTimeMillis();
			if (deviceId == null) {
				// 如果不存在这保存
				danmuMapper.saveDeviceToken(currentToken,timestamp);
			}else {
				//这里主要是更新设备的活跃时间
				danmuMapper.updateDeviceToken(deviceId,timestamp);
			}
		}
		return true;
	}
	// 友盟广播推送---向app的所有设备推送
	@Override
	public boolean uMengBroadcastPush(AndroidBroadcast broadcast, String content, String uuid) {
		boolean flag = false;
		try {
			broadcast.setTicker("Android broadcast ticker");
			broadcast.setTitle("中文的title");
			broadcast.setText("Android broadcast text");
			broadcast.goAppAfterOpen();
			broadcast.setDisplayType(DisplayType.NOTIFICATION);
			// TODO Set 'production_mode' to 'false' if it's a test device.
			// For how to register a test device, please see the developer doc.
			broadcast.setProductionMode();
			// Set customized fields
			broadcast.setExtraField("test", "helloworld");
			client.send(broadcast);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean alertuMengListcastPush(String content, String cityId, int minute) {
		try {


			unicast = new AndroidUnicast("5abc6307a40fa31dab000085", "83sstqceladbzomvbv7tkharqxxjjadd");
			//计算时间戳
			long timestamp = System.currentTimeMillis() - minute * 60 *1000;

			// 查询设备总数
			Integer deviceTotal = danmuMapper.countDeviceTokenAll(timestamp);
			System.out.print("======deviceTotal======"+deviceTotal);
			for (int pgnum = 0; pgnum <= deviceTotal / 500; pgnum++) {
				// 查询除当前设备外的500个
				List<String> deviceTokens = danmuMapper.selectDeviceTokenListAll(pgnum * 500, 500,timestamp);
				// 拼接要推送的设备deviceToken
				StringBuffer sb = new StringBuffer();
				for (String deviceToken : deviceTokens) {
					sb.append(deviceToken).append(",");
				}
				if(sb.length() > 0){
					// 设置列播的deviceToken
					unicast.setDeviceToken(sb.substring(0, sb.length() - 1));//去掉最后一个,
					unicast.setCustom("玛雅天气");
					unicast.setExtraField("content", content);// 预警内容
					unicast.setExtraField("cityId", cityId);// 城市id
					unicast.setExtraField("type", "3");// 预警类型
					unicast.setDisplayType(DisplayType.MESSAGE);
					unicast.setTestMode();// 正式模式
					client.send(unicast);// 推送
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 列播，给指定的设备发送，每次最多500
	@Override
	public boolean controluMengListcastPush(AndroidUnicast unicast, String content, String uuid, String currentToken,int minute) {
		try {
			
			// 判断当前token是否为空，不为空再查询保存--这里主要是更新设备的活跃时间
			if (!StringUtils.isEmpty(currentToken)) {
				// 查询设备信息，判断是否已经存在
				Integer deviceId = danmuMapper.selectDeviceToken(currentToken);
				//当前时间戳
				long timestamp = System.currentTimeMillis();
				if (deviceId == null) {
					// 如果不存在这保存
					danmuMapper.saveDeviceToken(currentToken,timestamp);
				}else {
					//这里主要是更新设备的活跃时间
					danmuMapper.updateDeviceToken(deviceId,timestamp);
				}
			}
			
			//计算时间戳
			long timestamp = System.currentTimeMillis() - minute * 60 *1000;
			
			// 查询设备总数
			Integer deviceTotal = danmuMapper.countDeviceToken(currentToken,timestamp);
			for (int pgnum = 0; pgnum <= deviceTotal / 500; pgnum++) {
				// 查询除当前设备外的500个
				List<String> deviceTokens = danmuMapper.selectDeviceTokenList(pgnum * 500, 500, currentToken,timestamp);
				// 拼接要推送的设备deviceToken
				StringBuffer sb = new StringBuffer();
				for (String deviceToken : deviceTokens) {
					sb.append(deviceToken).append(",");
				}
				// 设置列播的deviceToken
				unicast.setDeviceToken(sb.substring(0, sb.length() - 1));//去掉最后一个,
				unicast.setCustom("玛雅天气");
				unicast.setDisplayType(DisplayType.MESSAGE);
				unicast.setProductionMode();// 正式模式
				unicast.setExtraField("content", content);// 弹幕内容
				unicast.setExtraField("uuid", uuid);// 弹幕id
				client.send(unicast);// 推送
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	
	@Test
	public void sendAndroidBroadcast() throws Exception {
		// AndroidBroadcast broadcast = new
		// AndroidBroadcast("5abc6307a40fa31dab000085","83sstqceladbzomvbv7tkharqxxjjadd");
		AndroidUnicast unicast = new AndroidUnicast("5abc6307a40fa31dab000085", "83sstqceladbzomvbv7tkharqxxjjadd");
		// TODO Set your device token
		unicast.setDeviceToken("AsKi9D0IeIgmR5Dgg7Q2kG6_pObNfmL2PCVieFEgc7LI,");
		// unicast.setTicker( "玛雅天气");
		// unicast.setTitle("玛雅天气");
		// unicast.setText("玛雅天气");
		// unicast.goAppAfterOpen();
		unicast.setCustom("666666666666666");
		unicast.setDisplayType(DisplayType.MESSAGE);
		unicast.setProductionMode();
		unicast.setExtraField("content", "helloworld");
		unicast.setExtraField("uuid", "66666666666666666666666666");
		client.send(unicast);
	}

}
