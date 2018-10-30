package com.ddn.why.mapper.danmu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ddn.why.domain.danmu.Danmu;

public interface DanmuMapper {

	/**
	 * 保存弹幕内容
	 * @param content 内容
	 * @param time 时间戳
	 */
	void saveDanmu(@Param("content")String content,@Param("uuid")String uuid,@Param("timestamp")long time);
	
	/**
	 * 查询时间戳后的所有弹幕
	 * @param time
	 * @return
	 */
	List<Danmu> selectDanmuListByTime(@Param("timestamp")long timestamp);
	
	/**
	 * 查询设备，判断是否已经存在
	 * @param currentToken
	 * @return
	 */
	Integer selectDeviceToken(@Param("currentToken")String currentToken); 
	
	/**
	 * 保存设备token
	 * @param deviceToken
	 */
	void saveDeviceToken(@Param("currentToken")String currentToken,@Param("createtime")Long cretetime);
	
	/**
	 * 统计当前设备打开应用从timestamp到现在的这段时间内，除当前设备外一共有多少个设备打开
	 * @param currentToken	当前设备token
	 * @param timestamp minute分钟前的时间戳
	 * @return
	 */
	Integer countDeviceToken(@Param("currentToken")String currentToken,@Param("timestamp")long timestamp);

	/**
	 * 统计当前设备打开应用从timestamp到现在的这段时间内，除当前设备外一共有多少个设备打开
	 * @param timestamp minute分钟前的时间戳
	 * @return
	 */
	Integer countDeviceTokenAll(@Param("timestamp")long timestamp);

	/**
	 * 分页查询出当前设备外的所有设备token，加上时间判断
	 * @param start 
	 * @param rows
	 * @param currentToken
	 * @return
	 */
	List<String> selectDeviceTokenList(@Param("start")int start,@Param("rows")int rows,@Param("currentToken")String currentToken,@Param("timestamp")long timestamp);

	/**
	 * 分页查询出的所有设备token，加上时间判断
	 * @param start
	 * @param rows
	 * @return
	 */
	List<String> selectDeviceTokenListAll(@Param("start")int start,@Param("rows")int rows,@Param("timestamp")long timestamp);

	/**
	 * 更新设备的活跃时间
	 * @param deviceId 设备id
	 * @param timestamp 时间戳
	 */
	void updateDeviceToken(@Param("id")Integer deviceId, @Param("updatetime")long timestamp);
	
}
