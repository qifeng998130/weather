package com.ddn.why.mapper.moji;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.moji.MojiJson;

//生活指数数据
public interface IndexMapper {

	/**
	 * 根据cityid查询生活指数的json
	 * @param cityid
	 * @return
	 */
	MojiJson selectIndexDataById(@Param("cityid")String cityid);

	/**
	 * 保存限行数据json
	 * @param cityId
	 * @param name
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void saveIndexJson(@Param("cityid")String cityId, @Param("city")String city, @Param("indexJson")String dataJson, @Param("createtime")String createtime);

	/**
	 * 更新限行数据json
	 * @param cityId
	 * @param jsonString
	 * @param dateYMDHMS
	 */
	void updateIndexJson(@Param("cityid")String cityId, @Param("indexJson")String dataJson,  @Param("updatetime")String updatetime);
	
	
	
}
