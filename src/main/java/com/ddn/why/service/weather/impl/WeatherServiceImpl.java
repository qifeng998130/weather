package com.ddn.why.service.weather.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddn.why.domain.moji.ForecastInfo;
import com.ddn.why.domain.weather.Air;
import com.ddn.why.domain.weather.City;
import com.ddn.why.domain.weather.CityWeather;
import com.ddn.why.domain.weather.CurrentConditions;
import com.ddn.why.domain.weather.ForecastCondi;
import com.ddn.why.domain.weather.Hourly;
import com.ddn.why.domain.weather.Index;
import com.ddn.why.mapper.weather.WeatherMapper;
import com.ddn.why.service.weather.WeatherService;
import com.ddn.why.utils.DateUtils;
import com.ddn.why.utils.HttpUtils;

//及速api
@Service
public class WeatherServiceImpl implements WeatherService {

	private static final String Ali_AppCode = "9ff0edbdeb36450aae385fb34d4c291a";
	private static final String Ali_HOST = "http://jisutianqi.market.alicloudapi.com";
	private static final String Ali_PATH = "/weather/query";
	private static final String Ali_METHOD = "GET";

	@Autowired
	private WeatherMapper weatherMapper;

	// 解析获取需要的对象再封装成map返回
	@Override
	public Map<String, Object> getAliWeatherInfo(JSONObject jb) {
		// System.out.println("json字符串>>>>>"+jb);
		// 第一部分：获取city，city_name,current_date_time
		String city = jb.getString("citycode");
		String city_name = jb.getString("city");
		String current_date_time = DateUtils.dateStr2Millis(jb.getString("updatetime"));
		ForecastInfo forecastInfo = new ForecastInfo(city, city_name, current_date_time);

		// 第二部分：天气+生活指数+空气质量
		String week = jb.getString("week");// 星期
		String weather = jb.getString("weather");// 天气
		String temp = jb.getString("temp");// 气温
		String tempurature_c = temp;
		String tempurature = tempurature_c + "℃";
		String temphigh = jb.getString("temphigh");// 最高气温
		String templow = jb.getString("templow");// 最低气温
		String icon = jb.getString("img");// 图标
		String humidity = jb.getString("humidity");// 湿度
		String pressure = jb.getString("pressure");// 气压
		String windspeed = jb.getString("windspeed");// 风速
		String winddirect = jb.getString("winddirect");// 风向
		String windpower = jb.getString("windpower");// 风级
		String update_time = DateUtils.dateStr2Millis(jb.getString("updatetime"));
		// 获取生活指数
		JSONArray indexJson = jb.getJSONArray("index");
		String indexStr = JSONObject.toJSONString(indexJson);
		List<Index> indexlist = JSONObject.parseArray(indexStr, Index.class);
		// 获取空气质量
		JSONObject airJson = jb.getJSONObject("aqi");
		String quality = airJson.getString("quality");
		String aqi = airJson.getString("aqi");// 空气质量指数
		String pm10 = airJson.getString("pm10");// pm10一小时
		String pm2_5 = airJson.getString("pm2_5");// pm2.5一小时
		String so2 = airJson.getString("so2");
		String no2 = airJson.getString("no2");
		String co = airJson.getString("co");
		String o3 = airJson.getString("o3");
		String level = airJson.getJSONObject("aqiinfo").getString("level");// 等级
		String color = airJson.getJSONObject("aqiinfo").getString("color");// 色值
		String airUpdate_time = DateUtils.dateStr2Millis(airJson.getString("timepoint"));// 更新时间戳
		Air air = new Air(quality, aqi, pm10, pm2_5, so2, no2, co, o3, level, color, airUpdate_time);
		// 创建currenConditions对象封装数据
		CurrentConditions currentConditions = new CurrentConditions(week, weather, tempurature_c, tempurature_c, tempurature, temphigh, templow, icon, humidity, pressure, windspeed, winddirect, windpower, update_time, indexlist, air);

		// 第三部分：未来一周
		String dailyJson = jb.getString("daily");
		List<ForecastCondi> dailylist = JSONObject.parseArray(dailyJson, ForecastCondi.class);

		// 第四部分：24小时
		String hourly = jb.getString("hourly");
		List<Hourly> hourlylist = JSONObject.parseArray(hourly, Hourly.class);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forecast_information", forecastInfo);
		map.put("current_conditions", currentConditions);
		map.put("forecast_conditions", dailylist);
		map.put("hourly", hourlylist);
		return map;
	}

	// 第一次保存天气信息，之后为修改，有则更新，无则保存
	@Override
	public void saveAndUpdateService(String citycode, String city, String weatherStr, String time) {
		// 查询天气信息
		CityWeather cityWeather = weatherMapper.selectCityWeather(citycode);
		// 判断是否存在
		if (cityWeather != null) {
			// 更新
			weatherMapper.updateWeather(citycode, weatherStr, time);
		} else {
			// 保存
			weatherMapper.saveWeather(citycode, city, weatherStr, time);
		}
	}

	// 查询本地天气信息
	@Override
	public String getDdnWeatherInfo(String citycode) {
		// 查询天气信息
		CityWeather cityWeather = weatherMapper.selectCityWeather(citycode);
		return cityWeather.getWeather();
	}

	// 查询本地数据并判断更新时间是否在半小时内
	@Override
	public String getDdnCityWeatherService(String citycode) {
		// 查询天气信息
		CityWeather cityWeather = weatherMapper.selectCityWeather(citycode);
		// System.out.println(cityWeather);
		// 存在且更新时间在半小时内
		if (cityWeather != null && DateUtils.dateAmongMin(cityWeather.getUpdatetime(), 30)) {
			return cityWeather.getWeather();
		} else {
			return null;
		}
	}

	// 从ali获取数据并备份（有则更新，无则保存）
	@Override
	public Map<String, Object> saveAndgetAliCityWeatherService(String citycode) {
		// 请求配置
		String host = Ali_HOST;// 链接
		String path = Ali_PATH;// 路径
		String method = Ali_METHOD;// get
		String appcode = Ali_AppCode;// appcode

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("citycode", citycode);
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// 获取response的body
			String result = EntityUtils.toString(response.getEntity());
			// System.out.println(result);
			JSONObject json = JSONObject.parseObject(result);
			// 获取返回的状态值
			String status = json.getString("status");
			// 判断状态
			if (status != null && status.equals("0")) {
				JSONObject jb = json.getJSONObject("result");
				// 第一部分：获取city，city_name,current_date_time
				String city = jb.getString("citycode");
				String city_name = jb.getString("city");
				String current_date_time = DateUtils.dateStr2Millis(jb.getString("updatetime"));
				ForecastInfo forecastInfo = new ForecastInfo(city, city_name, current_date_time);

				// 第二部分：天气+生活指数+空气质量
				String week = jb.getString("week");// 星期
				String weather = jb.getString("weather");// 天气
				String temp = jb.getString("temp");// 气温
				String tempurature_c = temp;
				String tempurature = tempurature_c + "℃";
				String temphigh = jb.getString("temphigh");// 最高气温
				String templow = jb.getString("templow");// 最低气温
				String icon = jb.getString("img");// 图标
				String humidity = jb.getString("humidity");// 湿度
				String pressure = jb.getString("pressure");// 气压
				String windspeed = jb.getString("windspeed");// 风速
				String winddirect = jb.getString("winddirect");// 风向
				String windpower = jb.getString("windpower");// 风级
				String update_time = DateUtils.dateStr2Millis(jb.getString("updatetime"));
				// 获取生活指数
				JSONArray indexJson = jb.getJSONArray("index");
				String indexStr = JSONObject.toJSONString(indexJson);
				List<Index> indexlist = JSONObject.parseArray(indexStr, Index.class);
				// 获取空气质量
				JSONObject airJson = jb.getJSONObject("aqi");
				String quality = airJson.getString("quality");
				String aqi = airJson.getString("aqi");// 空气质量指数
				String pm10 = airJson.getString("pm10");// pm10一小时
				String pm2_5 = airJson.getString("pm2_5");// pm2.5一小时
				String so2 = airJson.getString("so2");
				String no2 = airJson.getString("no2");
				String co = airJson.getString("co");
				String o3 = airJson.getString("o3");
				String level = airJson.getJSONObject("aqiinfo").getString("level");// 等级
				String color = airJson.getJSONObject("aqiinfo").getString("color");// 色值
				String airUpdate_time = DateUtils.dateStr2Millis(airJson.getString("timepoint"));// 更新时间戳
				Air air = new Air(quality, aqi, pm10, pm2_5, so2, no2, co, o3, level, color, airUpdate_time);
				// 创建currenConditions对象封装数据
				CurrentConditions currentConditions = new CurrentConditions(week, weather, tempurature_c, tempurature_c, tempurature, temphigh, templow, icon, humidity, pressure, windspeed, winddirect, windpower, update_time, indexlist, air);

				// 第三部分：未来一周
				String dailyJson = jb.getString("daily");
				List<ForecastCondi> dailylist = JSONObject.parseArray(dailyJson, ForecastCondi.class);

				// 第四部分：24小时
				String hourly = jb.getString("hourly");
				List<Hourly> hourlylist = JSONObject.parseArray(hourly, Hourly.class);
				for (Hourly hourly2 : hourlylist) {
					String time = hourly2.getTime();
					hourly2.setTime(time.substring(0, time.lastIndexOf(":")));
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("forecast_information", forecastInfo);
				map.put("current_conditions", currentConditions);
				map.put("forecast_conditions", dailylist);
				map.put("hourly", hourlylist);
				// 第一次保存，之后更新
				saveAndUpdateService(citycode, city_name, JSON.toJSONString(map), jb.getString("updatetime"));
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// 保存城市列表
	@Override
	public void saveCityList(List<City> citylist) {
		for (City city : citylist) {
			System.out.println(city);
		}
		weatherMapper.saveCityList(citylist);
	}

	// 查询citycode
	@Override
	public Map<String, Object> selectCityCodeService(String district, String city, String province) {
		Map<String, Object> map = new HashMap<>();
		City cityInfo = weatherMapper.selectCityCode(district, city, province);
		if (cityInfo == null) {
			map.put("msg", "不存在此城市");
		} else {
			map.put("msg", "OK");
			map.put("data", cityInfo);
		}
		return map;
	}

}
