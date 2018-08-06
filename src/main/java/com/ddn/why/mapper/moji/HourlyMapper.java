package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//小时预报数据
public interface HourlyMapper {

	/**
	 * 根据cityid查询限行json
	 * @param cityid
	 * @return
	 */
	MojiJson selectHourlyDataById(@Param("cityid")String cityid);

	/**
	 * 保存限行数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveHourlyJson(@Param("cityid")String cityId, @Param("city")String city, @Param("hourlyJson")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新限行数据json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateHourlyJson(@Param("cityid")String cityId, @Param("hourlyJson")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
