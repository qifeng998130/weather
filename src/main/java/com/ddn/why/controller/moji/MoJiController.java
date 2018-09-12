package com.ddn.why.controller.moji;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddn.why.domain.moji.MoJiHot;
import com.ddn.why.service.moji.MojiService;

//墨迹天气数据
@Controller
@RequestMapping("/weather")
public class MoJiController {

	@Autowired
	private MojiService mojiService;

	/**
	 * cityId版热门城市
	 * 
	 * @return
	 */
	@RequestMapping("/MoJiHotCitys")
	@ResponseBody
	public List<MoJiHot> getMojiHots() {
		return mojiService.selectMoJiHotsService();
	}
	
	
	@RequestMapping("/getMoJiCityId")
	@ResponseBody
	public Map<String, Object> getMoJiCityId(String district,String province, String city){
		//System.out.println(district+"---"+province);
		Map<String, Object> resultMap = mojiService.selectMoJiCityId(district,province,city);
		return resultMap;
	}

	/**
	 * 玛雅天气数据接口
	 * 
	 * 墨迹数据 先根据citycode从数据库获取，如果存在并且更新时间在半小时内则把对应的weather转换成json返回
	 * 如果数据库不存在或者更新时间超过半小时则从ali接口获取，获取后，判断数据库中是否已经存此citycode的天气信息，有则更新，无则保存
	 * 
	 * @param
	 * @return
	 *
	 */
	@RequestMapping("/getMoJiWeather")
	@ResponseBody
	public Map<String, Object> getWeatherInfo(@RequestBody String[] cityIds,HttpServletRequest req) {
		//System.out.println("----------------------");
		//细化处理墨迹api
		Map<String, Object> resultMap = new HashMap<>();
		for (int i = 0; i < cityIds.length; i++) {
			String cityId = cityIds[i];
			String location = null;
			Map<String, Object> MoJiWeather = new HashMap<>();
			try {
				boolean flag = cityId.matches("[0-9]{1,}");
				if(!flag){

					JSONObject cityJson= JSONObject.parseObject(cityId);
					cityId = cityJson.getString("cityid");
					location = cityJson.getString("location");
				}
				//获取细化处理后的数据
				MoJiWeather = mojiService.saveAndgetMoJiDataService(cityId, location, req);
				resultMap.put("msg", "OK");
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("msg", "ERROR");
			}
			resultMap.put(cityId, MoJiWeather);
		}
		return resultMap;
		
		//统一处理
//		Map<String, Object> resultMap = new HashMap<>();
//		for (int i = 0; i < cityIds.length; i++) {
//			String cityId = cityIds[i];
//			resultMap.put("msg", "OK");
//			// 从数据库查询
//			String ddnWeather = mojiService.getDdnMoJiWeatherService(cityId);
//			if (ddnWeather != null) {
////				System.out.println("从数据库获取数据");
//				// 数据库weather字符串转换
//				resultMap.put(cityId, JSON.parse(ddnWeather));
//			} else {
//				// 数据库获取失败则从墨迹获取
//				// 获取墨迹数据并保存更新数据库
//				Map<String, Object> MoJiWeather = mojiService.saveAndgetMoJiWeatherService(cityId,req);
//				if (MoJiWeather != null) {
////					System.out.println("从墨迹天气获取数据");
//					MoJiWeather.put("source", "moji");
//					resultMap.put(cityId, MoJiWeather);
//				} else {
//					System.out.println("没有数据");
//					resultMap.put("msg", "ERROR");
//				}
//			}
//		}
//		return resultMap;
		
		
	}
	
	/**
	 * 细化处理墨迹api
	 * @param cityIds
	 * @param req
	 * @return
	 */
	@RequestMapping("/getWeatherData")
	@ResponseBody
	public Map<String, Object> getWeatherData(@RequestBody String[] cityIds,HttpServletRequest req){
		Map<String, Object> resultMap = new HashMap<>();
		for (int i = 0; i < cityIds.length; i++) {
			String cityId = cityIds[i];
			//获取细化处理后的数据
			Map<String, Object> MoJiWeather = new HashMap<>();
			try {
				MoJiWeather = mojiService.saveAndgetMoJiDataService(cityId,null, req);
				resultMap.put("msg", "OK");
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("msg", "ERROR");
			}
			resultMap.put(cityId, MoJiWeather);
		}
		return resultMap;
	}

}
