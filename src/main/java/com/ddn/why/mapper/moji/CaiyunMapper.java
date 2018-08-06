package com.ddn.why.mapper.moji;

import com.ddn.why.domain.moji.CaiyunJson;
import com.ddn.why.domain.moji.MojiJson;
import org.apache.ibatis.annotations.Param;


public interface CaiyunMapper {

	/**
	 * 根据cityid查询限行json
	 * @param cityid
	 * @return
	 */
	CaiyunJson selectCaiyunDataById(@Param("cityid") String cityid);

	/**
	 * 保存限行数据json
	 * @param cityId
     * @param city
	 */
	void saveCaiyunDataJson(@Param("cityid") String cityId, @Param("city") String city, @Param("conditionJson") String condition,
                            @Param("hourlyJson") String hourly_array, @Param("forecastsJson") String forecast,
							@Param("alertJson") String alertJson,
                            @Param("createtime") String createtime);

	/**
	 * 更新限行数据json
     * @param cityId
	 *
	 */
	void updateCaiyunDataJson(@Param("cityid") String cityId,@Param("conditionJson") String condition,
                              @Param("hourlyJson") String hourly_array, @Param("forecastsJson") String forecast,
							  @Param("alertJson") String alertJson,
                              @Param("updatetime") String updatetime);
	
	
	
}
