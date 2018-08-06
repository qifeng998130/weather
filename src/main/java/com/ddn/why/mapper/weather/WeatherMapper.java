package com.ddn.why.mapper.weather;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.weather.City;
import com.ddn.why.domain.weather.CityWeather;

public interface WeatherMapper {

	/**
	 * 根据citycode查询citycode,判断是否已经存在城市天气信息
	 * @param citycode
	 * @return
	 */
	CityWeather selectCityWeather(String citycode);

	/**
	 * 更新天气信息
	 * @param citycode
	 * @param weatherStr
	 */
	void updateWeather(@Param("citycode")String citycode, @Param("weather")String weatherStr,@Param("updatetime")String updatetime);

	/**
	 * 保存天气信息
	 * @param citycode
	 * @param city
	 * @param weatherStr
	 * @param string 
	 */
	void saveWeather(@Param("citycode")String citycode, @Param("city")String city, @Param("weather")String weatherStr, @Param("createtime")String createtime);

	/**
	 * 保存城市信息
	 * @param citylist
	 */
	void saveCityList(@Param("citylist")List<City> citylist);
	
	/**
	 * 查询citycode
	 * @param district
	 * @param city
	 * @param province
	 * @return
	 */
	City selectCityCode(@Param("district")String district,@Param("city")String city,@Param("province")String province);
	
	/**
	 * 根据citycode查询墨迹城市id
	 * @param citycode
	 * @return
	 */
	String selectMoJiIdByCityCode(String citycode);
}
