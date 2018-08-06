package com.ddn.why.service.danmu.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ddn.why.domain.danmu.Danmu;
import com.ddn.why.mapper.danmu.DanmuMapper;
import com.ddn.why.service.danmu.DanmuService;

@Service
public class DanmuServiceImpl implements DanmuService {

	@Autowired
	private DanmuMapper danmuMapper;

	// 保存弹幕内容到本地
	@Override
	public void saveDanmuService(String content, String uuid) {
		long time = System.currentTimeMillis();
		danmuMapper.saveDanmu(content, uuid, time);
	}

	// 查询时间内的所有弹幕并保存设备信息
	@Override
	public List<Danmu> controlDanmuListService(int hour, String currentToken) {

		// 判断当前token是否为空，不为空再查询保存
		if (!StringUtils.isEmpty(currentToken)) {
			// 查询设备信息，判断是否已经存在
			Integer deviceId = danmuMapper.selectDeviceToken(currentToken);
			//当前时间戳
			long timestamp = System.currentTimeMillis();
			if (deviceId == null) {
				// 如果不存在这保存
				danmuMapper.saveDeviceToken(currentToken,timestamp);
			}else {
				//存在这更新
				danmuMapper.updateDeviceToken(deviceId,timestamp);
			}
		}

		//查询时间内的所有弹幕
		long timestamp = System.currentTimeMillis() - hour * 60 * 60 * 1000;
		List<Danmu> dlist = danmuMapper.selectDanmuListByTime(timestamp);
		// for (Danmu danmu : dlist) {
		// System.out.println(danmu);
		// }
		return dlist;
	}

}
