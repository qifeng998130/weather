package com.ddn.why.mapper.moji;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.City;
import com.ddn.why.domain.moji.MoJiHot;
import com.ddn.why.domain.moji.MoJiWeather;

public interface MoJIMapper {

	/**
	 * 热门城市列表
	 * @return
	 */
	List<MoJiHot> selectMoJIHotList();
	
	/**
	 * 根据cityId查询数据库中的天气数据
	 * @param cityId
	 * @return
	 */
	MoJiWeather selectMoJiWeatherById(String cityId);
	
	/**
	 * 保存天气数据
	 * @param cityId
	 * @param city
	 * @param weatherStr
	 * @param createtime
	 */
	void saveMoJiWeather(@Param("cityid")String cityId, @Param("city")String city, @Param("weather")String weatherStr, @Param("createtime")String createtime);
	
	/**
	 * 更新天气信息
	 * @param cityId
	 * @param weatherStr
	 */
	void updateMoJiWeather(@Param("cityid")String cityId, @Param("weather")String weatherStr,@Param("updatetime")String updatetime);

	/**
	 * 查询cityId
	 * @param district
	 * @param province
	 * @return
	 */
	City selectMoJiCityId(@Param("district")String district,@Param("province")String province);
	
	/**
	 * 根据cityid查询城市名及省
	 * @param cityId
	 * @return
	 */
	City selectCityNameById(@Param("cityid")String cityId);


	/**
	 * 根据cityid查询城市名及省
	 * @param cityId
	 * @return
	 */
	void updateCityLocationById(@Param("cityid")String cityId, @Param("location")String location);

	/**
	 * 保存请求墨迹数据的次数
	 */
	void saveMoJICount();

	/**
	 * 保存日志
	 * @param
	 * @param
	 */
	void saveLog(@Param("ip")String ip,@Param("cityId")String cityId,@Param("api")String api, @Param("time")String time);
	
}
