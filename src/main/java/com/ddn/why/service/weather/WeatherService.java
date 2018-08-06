package com.ddn.why.service.weather;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ddn.why.domain.weather.City;

//及速api天气业务接口
public interface WeatherService {

	/**
	 * 解析阿里数据
	 * @param jb
	 * @return
	 */
	Map<String, Object> getAliWeatherInfo(JSONObject jb);

	/**
	 * 数据备份处理
	 * @param citycode
	 * @param city
	 * @param weatherStr
	 */
	void saveAndUpdateService(String citycode, String city, String weatherStr,String time);

	/**
	 * 查询本地天气数据
	 * @param citycode
	 * @return
	 */
	String getDdnWeatherInfo(String citycode);

	/**
	 * 从数据库插叙天气数据并判断更新时间
	 * @param citycode
	 * @return
	 */
	String getDdnCityWeatherService(String citycode);

	/**
	 * 从ali获取数据
	 * @param citycode
	 * @return
	 */
	Map<String, Object> saveAndgetAliCityWeatherService(String citycode);

	/**
	 * 保存城市列表
	 * @param citylist
	 */
	void saveCityList(List<City> citylist);
	
	/**
	 * 查询citycode
	 * @param district
	 * @param city
	 * @param province
	 * @return
	 */
	Map<String, Object> selectCityCodeService(String district,String city,String province);

	

}
