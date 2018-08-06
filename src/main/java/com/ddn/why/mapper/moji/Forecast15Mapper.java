package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//未来15天
public interface Forecast15Mapper {

	/**
	 * 根据cityid查询限行json
	 * @param cityid
	 * @return
	 */
	MojiJson selectForecast15DataById(@Param("cityid")String cityid);

	/**
	 * 保存限行数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveForecast15Json(@Param("cityid")String cityId, @Param("city")String city, @Param("Forecast15Json")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新限行数据json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateForecast15Json(@Param("cityid")String cityId, @Param("Forecast15Json")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
