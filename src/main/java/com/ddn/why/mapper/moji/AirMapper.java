package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//空气质量指数数据
public interface AirMapper {

	/**
	 * 根据cityid查询空气质量指数的json
	 * @param cityid
	 * @return
	 */
	MojiJson selectAirDataById(@Param("cityid")String cityid);

	/**
	 * 保存空气质量指数数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveAirJson(@Param("cityid")String cityId, @Param("city")String city, @Param("airJson")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新空气质量指数json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateAirJson(@Param("cityid")String cityId, @Param("airJson")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
