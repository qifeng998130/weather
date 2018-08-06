package com.ddn.why.controller.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ddn.why.domain.weather.City;
import com.ddn.why.domain.weather.HotCity;
import com.ddn.why.service.moji.MojiService;
import com.ddn.why.service.weather.HotCityService;
import com.ddn.why.service.weather.WeatherService;
import com.ddn.why.utils.HttpUtils;

//极速api
@Controller
@RequestMapping("/weather")
public class MyController {
	
	@Autowired
	private HotCityService hotCityService;
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private MojiService mojiService;

	/**
	 * citycode版热门城市
	 * @return
	 */
	@RequestMapping("/hotcity")
	@ResponseBody
	public List<HotCity> getHotCityList(){
		return hotCityService.seleHotCitieListService();
	}
	
	
	/**
	 * 先根据citycode从数据库获取，如果存在并且更新时间在半小时内则把对应的weather转换成json返回
	 * 如果数据库不存在或者更新时间超过半小时则从ali接口获取，获取后，判断数据库中是否已经存此citycode的天气信息，有则更新，无则保存
	 * @param citycode
	 * @return
	 */
	@RequestMapping("/getWeather")
	@ResponseBody
	public Map<String, Object> getWeather(@RequestBody String[] citycodes){
		Map<String, Object> resultMap = new HashMap<>();
//		String[] citycodes = cityArray.getCitycodes();
		for (int i = 0; i < citycodes.length; i++) {
			String citycode = citycodes[i];
			System.out.println(citycode);
			resultMap.put("msg", "OK");
			String ddnWeather = weatherService.getDdnCityWeatherService(citycode);
			//从数据库获取：存在并且更新时间在一小时内
			if(ddnWeather != null){
				System.out.println("从数据库获取数据");
				resultMap.put("source", "ddn");
				//数据库weather字符串转换
				resultMap.put(citycode, JSON.parse(ddnWeather));
			}else {
				//数据库获取失败则从ali获取
				//获取ali数据并保存更新数据库
				Map<String, Object> aliWeather = weatherService.saveAndgetAliCityWeatherService(citycode);
				if(aliWeather != null){
					System.out.println("从阿里接口获取数据");
					resultMap.put(citycode, aliWeather);
					resultMap.put("source", "ali");
				}else {
					System.out.println("没有数据");
					resultMap.put("msg", "ERROR");
				}
			}
		}
		return resultMap;
	}
	
	
	//城市天气
	@RequestMapping("/getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(String citycode){
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println(citycode);
		String host = "http://jisutianqi.market.alicloudapi.com";
	    String path = "/weather/query";
	    String method = "GET";
	    String appcode = "9ff0edbdeb36450aae385fb34d4c291a";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("city", citycode);
//	    querys.put("citycode", citycode);
//	    querys.put("cityid", "cityid");
//	    querys.put("ip", "ip");
//	    querys.put("location", citycode);

	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//获取response的body
	    	String result = EntityUtils.toString(response.getEntity());
	    	System.out.println(result);
	    	JSONObject jsonObject = JSONObject.parseObject(result);
	    	//获取返回的状态值
	    	String status = jsonObject.getString("status");
	    	if(status.equals("0")){//访问接口成功
	    		JSONObject jsonResult = jsonObject.getJSONObject("result");
		    	//获取数据并封装成map
		    	Map<String, Object> map = weatherService.getAliWeatherInfo(jsonResult);
		    	String weatherStr = JSON.toJSONString(map);
		    	//本地数据库处理
		    	String city = jsonResult.getString("city");
		    	//第一次保存，之后为修改
		    	weatherService.saveAndUpdateService(citycode,city,weatherStr,jsonResult.getString("updatetime"));
		    	resultMap.put("source", "ali");
		    	resultMap.put("result", map);
	    	}else {
				//访问接口失败，从数据库取天气信息
	    		String cityweather = weatherService.getDdnWeatherInfo(citycode);
//	    		System.out.println("---------------------------------------------");
	    		resultMap.put("source", "ddn");
	    		resultMap.put("result", JSON.parseObject(cityweather));
		    	String weatherStr = JSON.toJSONString(resultMap);
		    	System.out.println("返回的json字符串>>>>>>>"+weatherStr);
			}
	    	resultMap.put("msg", "OK");
	    } catch (Exception e) {
	    	resultMap.put("msg", "ERROR--->"+e);
	    	e.printStackTrace();
	    }
	    
		return resultMap;
	}
	
	//根绝城市名获取citycode
	@RequestMapping("/getCityCode")
	@ResponseBody
	public Map<String, Object> getCityCode(String district,String city,String province){
//		System.out.println(district+"-----"+city+"-----"+province);
		return weatherService.selectCityCodeService(district, city, province);
	}
	
	//城市citycode
	@Test
	@RequestMapping("/getCityList")
	public void cityCode(){
		String host = "http://jisutianqi.market.alicloudapi.com";
	    String path = "/weather/city";
	    String method = "GET";
	    String appcode = "fcb510ee459f48dd9800bf6bff560bb0";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();

	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//	    	System.out.println(response.toString());
	    	//获取response的body
	    	String result = EntityUtils.toString(response.getEntity());
	    	JSONObject json = JSONObject.parseObject(result);
	    	String cityJson = json.getString("result");
	    	List<City> citylist = JSONObject.parseArray(cityJson, City.class);
	    	weatherService.saveCityList(citylist);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	@Test
	public void text(){
		String[] citycodes = {"1010101","10303030"};
		System.out.println(JSONObject.toJSONString(citycodes));
		
	}
	
}
