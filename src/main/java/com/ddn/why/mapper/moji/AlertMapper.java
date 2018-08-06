package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//天气预警数据
public interface AlertMapper {

	/**
	 * 根据cityid查询天气预警的json
	 * @param cityid
	 * @return
	 */
	MojiJson selectAlertDataById(@Param("cityid")String cityid);

	/**
	 * 保存天气预警数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveAlertJson(@Param("cityid")String cityId, @Param("city")String city, @Param("alertJson")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新天气预警的json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateAlertJson(@Param("cityid")String cityId, @Param("alertJson")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
