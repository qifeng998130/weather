package com.ddn.why.service.moji;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ddn.why.domain.moji.MoJiHot;

public interface MojiService {

	static final boolean switch_moji = false;

	// 数据库中更新时间差
	static final int UPDATE_MOJI_TIME = 30;
	// 实况token
	static final String CONDITION_TOKEN = "50b53ff8dd7d9fa320d3d3ca32cf8ed1";
	// 空气质量指数token
	static final String AQI_TOKEN = "8b36edf8e3444047812be3a59d27bab9";
	// 未来24小时token
	static final String FORECAST24HOURS_TOKEN = "008d2ad9197090c5dddc76f583616606";
	// aqi未来5天token
	static final String AQIFORECAST5DAYS_TOKEN = "0418c1f4e5e66405d33556418189d2d0";
	// 未来15天token
	static final String FORECAST15DAYS_TOKEN = "f9f212e1996e79e0e602b08ea297ffb0";
	// 生活指数token
	static final String INDEX_TOKEN = "5944a84ec4a071359cc4f6928b797f91";
	// 限行token
	static final String LIMIT_TOKEN = "27200005b3475f8b0e26428f9bfb13e9";
	// 天气预警token
	static final String ALERT_TOKEN = "7ebe966ee2e04bbd8cdbc0b84f7f3bc7";
	
	//限行城市
	static final String[] LIMIT_CITYS = {"北京市","天津市","贵阳市","兰州市","杭州市","成都市"};
	
	//实况天气更新频率：30分钟
	static final int CURRENT_UPDATE_TIME = 30;
	//生活指数更新频率：1440分钟（一天）
	static final int INDEX_UPDATE_TIME = 180;
	//空气质量指数更新频率：1440分钟
	static final int AIR_UPDATE_TIME = 1440;
	//小时天气数据更新频率：60分钟
	static final int HOURLY_UPDATE_TIME = 60;
	//未来15天预报和AQI未来5天的更新频率：1440分钟
	static final int FORECAST_UPDATE_TIME = 1440;
	//限行数据更新频率：1440
	static final int LIMIT_UPDATE_TIME = 1440;
	

	/**
	 * 处理墨迹天气数据
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	Map<String, Object> saveAndgetMoJiWeatherService(String citycode,HttpServletRequest req);

	/**
	 * 查询本地数据库的墨迹天气数据
	 * 
	 * @param cityId
	 * @return
	 */
	String getDdnMoJiWeatherService(String cityId);

	/**
	 * 查询热门城市
	 * 
	 * @return
	 */
	List<MoJiHot> selectMoJiHotsService();

	/**
	 * 查询cityId
	 * 
	 * @param district
	 * @param province
	 * @return
	 */
	Map<String, Object> selectMoJiCityId(String district, String province, String cityname);


	/**
	 * 细化处理秘籍数据api
	 * @param cityId
	 * @param req
	 * @return
	 */
	Map<String, Object> saveAndgetMoJiDataService(String cityId, String location, HttpServletRequest req);

}
