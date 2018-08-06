package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;


public interface LimitMapper {

	/**
	 * 根据cityid查询限行json
	 * @param cityid
	 * @return
	 */
	MojiJson selectLimitDataById(@Param("cityid")String cityid);

	/**
	 * 保存限行数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveLimitJson(@Param("cityid")String cityId, @Param("city")String city, @Param("limitJson")String limitJson, @Param("createtime")String createtime);

	/**
	 * 更新限行数据json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateLimitJson(@Param("cityid")String cityId, @Param("limitJson")String limitJson,  @Param("updatetime")String updatetime);
	
	
	
}
