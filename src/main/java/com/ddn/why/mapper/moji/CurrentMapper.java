package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//实时天气数据（没有生活指数和空气质量指数）
public interface CurrentMapper {

	/**
	 * 根据cityid查询数据json
	 * @param cityid
	 * @return
	 */
	MojiJson selectCurrentDataById(@Param("cityid")String cityid);

	/**
	 * 保存数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveCurrentJson(@Param("cityid")String cityId, @Param("city")String city, @Param("currentJson")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新数据json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateCurrentJson(@Param("cityid")String cityId, @Param("currentJson")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
