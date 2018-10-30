package com.ddn.why.service.moji.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ddn.why.utils.umeng.AndroidNotification;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddn.why.domain.moji.Air;
import com.ddn.why.domain.moji.Alert;
import com.ddn.why.domain.moji.AqiForecast;
import com.ddn.why.domain.moji.City;
import com.ddn.why.domain.moji.Condition;
import com.ddn.why.domain.moji.Forecast;
import com.ddn.why.domain.moji.ForecastInfo;
import com.ddn.why.domain.moji.Hourly;
import com.ddn.why.domain.moji.Index;
import com.ddn.why.domain.moji.Limit;
import com.ddn.why.domain.moji.MoJiHot;
import com.ddn.why.domain.moji.MoJiWeather;
import com.ddn.why.domain.moji.MojiJson;
import com.ddn.why.mapper.moji.AirMapper;
import com.ddn.why.mapper.moji.AlertMapper;
import com.ddn.why.mapper.moji.CurrentMapper;
import com.ddn.why.mapper.moji.Forecast15Mapper;
import com.ddn.why.mapper.moji.HourlyMapper;
import com.ddn.why.mapper.moji.IndexMapper;
import com.ddn.why.mapper.moji.LimitMapper;
import com.ddn.why.mapper.moji.MoJIMapper;
import com.ddn.why.service.moji.MojiService;
import com.ddn.why.service.caiyun.impl.CaiyunServiceImpl;
import com.ddn.why.service.umeng.impl.UmengServiceImpl;

import com.ddn.why.utils.umeng.AndroidBroadcast;
import com.ddn.why.utils.DateUtils;


import com.ddn.why.utils.HttpUtils;

@Service
public class MojiServiceImpl implements MojiService {

    @Autowired
    private CaiyunServiceImpl caiyunServiceImpl;
    @Autowired
    private UmengServiceImpl umengServiceIml;
    @Autowired
    private MoJIMapper moJIMapper;
    @Autowired
    private LimitMapper limitMapper;
    @Autowired
    private Forecast15Mapper forecast15Mapper;
    @Autowired
    private HourlyMapper hourlyMapper;
    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private AirMapper airMapper;
    @Autowired
    private CurrentMapper currentMapper;
    @Autowired
    private AlertMapper alertMapper;

    /**
     * 热门城市
     */
    @Override
    public List<MoJiHot> selectMoJiHotsService() {
        return moJIMapper.selectMoJIHotList();
    }

    // 查询cityId
    @Override
    public Map<String, Object> selectMoJiCityId(String district, String province, String cityname) {
        Map<String, Object> map = new HashMap<>();
        City city = moJIMapper.selectMoJiCityId(district, province);
        if (city == null) {
            city = moJIMapper.selectMoJiCityIdByName(cityname);
            if(city == null){
                map.put("msg", "不存在此城市");

            } else {

                map.put("msg", "OK");
                map.put("data", city);
            }
        } else {
            // 设置地址名为查询条件中的地址名
            city.setName(district);
            map.put("msg", "OK");
            map.put("data", city);
        }
        return map;
    }




    /**
     * 根据cityId获取墨迹天气数据json
     * <p>
     * http://aliv18.data.moji.com/whapi/json/alicityweather/limit 限行
     * http://aliv18.data.moji.com/whapi/json/alicityweather/aqi 空气质量指数
     * http://aliv18.data.moji.com/whapi/json/alicityweather/forecast24hours
     * 24小时
     * http://aliv18.data.moji.com/whapi/json/alicityweather/aqiforecast5days
     * aqi5天
     * http://aliv18.data.moji.com/whapi/json/alicityweather/forecast15days
     * 天气预报15天 http://aliv18.data.moji.com/whapi/json/alicityweather/condition
     * 天气实况 http://aliv18.data.moji.com/whapi/json/alicityweather/index 生活指数
     * http://aliv18.data.moji.com/whapi/json/alicityweather/alert 天气预警
     *
     * @param cityId
     * @return
     */
    public JSONObject getMoJiJsonResult(String api, String cityId, String token, HttpServletRequest req) {
        // System.out.println(api+"----"+cityId);
        String host = "http://aliv18.data.moji.com";
        String path = "/whapi/json/alicityweather/" + api;
        String method = "POST";
        String appcode = "9ff0edbdeb36450aae385fb34d4c291a";
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
        // 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        // 根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cityId", cityId);
        bodys.put("token", token);
        JSONObject json = null;
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

            // 保存请求次数
            moJIMapper.saveMoJICount();
            moJIMapper.saveLog(req.getRemoteAddr(), cityId, api, DateUtils.getDateYMDHMS());
            // System.out.println(response.toString());
            // 获取response的body
            String result = EntityUtils.toString(response.getEntity());
            JSONObject resultJson = JSONObject.parseObject(result);
            // code：0:正常请求 成功
            String code = resultJson.getString("code");
            if (code != null && code.equals("0")) {
                json = resultJson;
                // System.out.println("取到的json>>>>>"+json);
            } else {
                // c:1:token异常 2:签名错误 其它：系统错误
                String status = resultJson.getJSONObject("rc").getString("c");
                System.out.println("code:" + code + ",状态c：" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 获取墨迹天气数据1.0 统一处理，没有细化
     */
    @Override
    public Map<String, Object> saveAndgetMoJiWeatherService(String cityId, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();

        // 天气实况
        JSONObject json = getMoJiJsonResult("condition", cityId, CONDITION_TOKEN, req);
        JSONObject jb = json.getJSONObject("data");
        // 第一部分：获取city，city_name,current_date_time
        JSONObject cityJson = jb.getJSONObject("city");
        // String cityId = cityJson.getString("cityId");
        String district = cityJson.getString("name");// 区县
        // String city = cityJson.getString("pname");// 市
        // 实况天气
        JSONObject conditionJson = jb.getJSONObject("condition");
        String current_date_time = DateUtils.dateStr2Millis(conditionJson.getString("updatetime"));
        // 城市信息
        ForecastInfo forecastInfo = new ForecastInfo(cityId, district, current_date_time);

        map.put("forecast_information", forecastInfo);

        String weather = conditionJson.getString("condition");
        String conditionId = conditionJson.getString("conditionId");
        String humidity = conditionJson.getString("humidity");
        String temp = conditionJson.getString("temp");// 气温
        String icon = conditionJson.getString("icon");// 图标
        String pressure = conditionJson.getString("pressure");// 气压
        String windspeed = conditionJson.getString("windSpeed");// 风速
        String winddirect = conditionJson.getString("windDir");// 风向
        String windpower = conditionJson.getString("windLevel");// 风级
        String update_time = DateUtils.dateStr2Millis(conditionJson.getString("updatetime"));
        String realfeel = conditionJson.getString("realFeel");
        String uvi = conditionJson.getString("uvi");// 紫外线
        String tips = conditionJson.getString("tips");

        // 生活指数
        JSONObject dateJson = getMoJiJsonResult("index", cityId, INDEX_TOKEN, req).getJSONObject("data").getJSONObject("liveIndex");
        String indexJson = dateJson.getString(DateUtils.getDate());
        List<Index> indexs = JSONObject.parseArray(indexJson, Index.class);
        // 除去息斯敏
        for (int i = 0; i < indexs.size(); i++) {
            Index index = indexs.get(i);
            if (index.getName().equals("息斯敏过敏指数")) {
                indexs.remove(index);
            }
        }

        // 空气质量指数
        Air air = new Air("", "", "", "", "", "", "", "", "", "");
        String airJson = getMoJiJsonResult("aqi", cityId, AQI_TOKEN, req).getJSONObject("data").getString("aqi");
        air = JSONObject.parseObject(airJson, Air.class);

        Condition condition = new Condition(weather, temp, icon, humidity, pressure, windspeed, winddirect, windpower, update_time, conditionId, realfeel, tips, uvi, indexs, air);
        map.put("current_conditions", condition);

        // 未来15天
        JSONObject forecast15 = getMoJiJsonResult("forecast15days", cityId, FORECAST15DAYS_TOKEN, req);
        // 未来15天
        JSONArray forecastArray = forecast15.getJSONObject("data").getJSONArray("forecast");

        // 未来5天空气质量指数
        JSONObject aqiForecast = getMoJiJsonResult("aqiforecast5days", cityId, AQIFORECAST5DAYS_TOKEN, req);
        JSONArray aqiForecastArray = aqiForecast.getJSONObject("data").getJSONArray("aqiForecast");
        List<Forecast> forecasts = new ArrayList<>();
        if (forecastArray.size() > 0) {
            for (int i = 0; i < forecastArray.size(); i++) {
                JSONObject forecastJson = forecastArray.getJSONObject(i);
                String date = forecastJson.getString("predictDate");
                String updatetime = DateUtils.dateStr2Millis(forecastJson.getString("updatetime"));
                // 白天
                String conditionDay = forecastJson.getString("conditionDay");
                String imgDay = forecastJson.getString("conditionIdDay");
                String sunrise = forecastJson.getString("sunrise");
                String sunset = forecastJson.getString("sunset");
                String tempDay = forecastJson.getString("tempDay");
                String winddirectDay = forecastJson.getString("windDirDay");
                String windpowerDay = forecastJson.getString("windLevelDay") + "级";
                String windspeedDay = forecastJson.getString("windSpeedDay");
                // 夜间
                String conditionNight = forecastJson.getString("conditionNight");
                String imgNight = forecastJson.getString("conditionIdNight");
                String moonphase = forecastJson.getString("moonphase");
                String moonrise = forecastJson.getString("moonrise");
                String moonset = forecastJson.getString("moonset");
                String tempNight = forecastJson.getString("tempNight");
                String winddirectNight = forecastJson.getString("windDirNight");
                String windpowerNight = forecastJson.getString("windLevelNight") + "级";
                String windspeedNight = forecastJson.getString("windSpeedNight");

                // 可能为null的要处理
                AqiForecast aForecast = new AqiForecast("", "", "");
                if (i < aqiForecastArray.size()) {
                    // System.out.println(i+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    aForecast = aqiForecastArray.getObject(i, AqiForecast.class);
                }
                // 创建对象封装数据
                Forecast forecast = new Forecast(date, updatetime, conditionDay, imgDay, sunrise,
                        sunset, tempDay, winddirectDay, windpowerDay, windspeedDay, conditionNight,
                        imgNight, moonphase, moonrise, moonset, tempNight, winddirectNight,
                        windpowerNight, windspeedNight, aForecast, null,null);
                forecasts.add(forecast);
            }
        }
        map.put("forecast_conditions", forecasts);

        // 24小时天气
        String hourlyJson = getMoJiJsonResult("forecast24hours", cityId, FORECAST24HOURS_TOKEN, req).getJSONObject("data").getString("hourly");
        List<Hourly> hourlylist = JSONObject.parseArray(hourlyJson, Hourly.class);
        map.put("hourly", hourlylist);

        // 限行数据
        String limitJson = getMoJiJsonResult("limit", cityId, LIMIT_TOKEN, req).getJSONObject("data").getString("limit");
        List<Limit> limitList = JSONObject.parseArray(limitJson, Limit.class);
        // 判断limitList是否为null，为null则设置为""
        map.put("limit", limitList == null ? "" : limitList);

        // 保存数据
        map.put("source", "ddn");
        saveAndUpdateService(cityId, district, JSON.toJSONString(map), DateUtils.getDateYMDHMS());
        // System.out.println(JSON.toJSONString(map));
        return map;
    }

    /**
     * 判断数据库中是否有数据，有则更新，无则保存
     *
     * @param cityId
     * @param city
     * @param weatherStr
     * @param time
     */
    public void saveAndUpdateService(String cityId, String city, String weatherStr, String time) {
        // 查询天气信息
        MoJiWeather mojiWeather = moJIMapper.selectMoJiWeatherById(cityId);
        // 判断是否存在
        if (mojiWeather != null) {
            // 更新
            moJIMapper.updateMoJiWeather(cityId, weatherStr, time);
        } else {
            // 保存
            moJIMapper.saveMoJiWeather(cityId, city, weatherStr, time);
        }
    }

    // 查询数据库中墨迹天气数据
    @Override
    public String getDdnMoJiWeatherService(String cityId) {
        MoJiWeather mojiWeatherJson = moJIMapper.selectMoJiWeatherById(cityId);
        if (mojiWeatherJson != null && DateUtils.dateAmongMin(mojiWeatherJson.getUpdatetime(), UPDATE_MOJI_TIME)) {
            return mojiWeatherJson.getWeather();
        } else {
            return null;
        }
    }

    /*********************************************** 细化处理 ******************************************************/

    /**
     * 细化处理墨迹api2.0 对7个接口分别处理
     */
    @Override
    public Map<String, Object> saveAndgetMoJiDataService(String cityId, String location, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 根据id查询城市信息
        City city = moJIMapper.selectCityNameById(cityId);
        // System.out.println("cityname:" + city.getName() + "---province:" +
        // city.getProvince());
        // 城市信息
        ForecastInfo forecastInfo = new ForecastInfo(cityId, city.getName(), DateUtils.getDateYMDHMS());
        // 添加城市信息
        map.put("forecast_information", forecastInfo);
        String datasource = "";
        // 添加限行数据---每天
        map.put("limit", limitControl(cityId, city, req));

        if (switch_moji) {
            datasource = "数据来自墨迹天气";
            Condition current = currentControl(cityId, city.getName(), req);
            map.put("current_conditions", current);
            // 添加实况数据（current_conditions）---包含生活指数（每天）和空气质量（每天）
            // 添加未来15天数据和未来5天AQI（forecast_conditions）---每天
            map.put("forecast_conditions", forecast15daysControl(cityId, city.getName(), req));

            // 添加小时天气预报（hourly）---每小时
            map.put("hourly", hourlyControl(cityId, city.getName(), req));
            // 天气预警信息
            map.put("alert", alertControl(cityId, city.getName(), req));
            map.put("datasource", datasource);

        } else {
            datasource = "数据来自彩云天气";
            Map<String, Object> caiyunObj = caiyunServiceImpl.getCaiyunAppResult(city, location);
            List<Index> indexs = getShenghuozhishu(cityId, city.getName(), req);
            JSONObject caiyunapp_condition = (JSONObject) caiyunObj.get("condition");
            caiyunapp_condition.put("indexs", indexs);
            map.put("current_conditions", caiyunapp_condition);
            map.put("hourly", caiyunObj.get("hourly"));
            map.put("forecast_conditions", caiyunObj.get("forecasts"));
            // 天气预警信息
            map.put("alert", caiyunObj.get("alert"));

            map.put("datasource", datasource);

        }
        return map;

    }

    private List<Index> getShenghuozhishu(String cityId, String city, HttpServletRequest req){
            // 处理生活指数
            List<Index> indexs = new ArrayList<>();
            // 查询数据库
            MojiJson ddindexJson = indexMapper.selectIndexDataById(cityId);
            //System.out.println(ddindexJson);
            // 判断时间是否是当天更新的---DateUtils.dateAmongMin(ddindexJson.getUpdatetime(), INDEX_UPDATE_TIME)

            if (ddindexJson != null && DateUtils.dateAmongMin(ddindexJson.getUpdatetime(), INDEX_UPDATE_TIME)) {
                // System.out.println("index>>>>>>>来自豆豆鸟");
                // 转换成集合
                indexs = JSONArray.parseArray(ddindexJson.getDataJson(), Index.class);
            } else {
                // System.out.println("index>>>>>>>来自墨迹");
                // 生活指数
                JSONObject dateJson = getMoJiJsonResult("index", cityId, INDEX_TOKEN, req).getJSONObject("data").getJSONObject("liveIndex");
                String indexJson = dateJson.getString(DateUtils.getDate());
                //判空？？？
                indexs = JSONObject.parseArray(indexJson, Index.class);
                // 除去息斯敏指数
                for (int i = 0; i < indexs.size(); i++) {
                    Index index = indexs.get(i);
                    if (index.getName().equals("息斯敏过敏指数")) {
                        indexs.remove(index);
                    }
                }
                // 保存或者更新
                if (ddindexJson == null) {
                    // 保存
                    indexMapper.saveIndexJson(cityId, city, JSON.toJSONString(indexs), DateUtils.getDateYMDHMS());
                } else {
                    indexMapper.updateIndexJson(cityId, JSON.toJSONString(indexs), DateUtils.getDateYMDHMS());
                }
            }
            return indexs;
    }
    /**
     * 实时天气数据处理---每半小时，生活指数---当天，空气质量---当天
     *
     * @param cityId 城市id
     * @param city   城市名
     * @param req
     * @return
     */
    private Condition currentControl(String cityId, String city, HttpServletRequest req) {
        // 处理生活指数
        List<Index> indexs = new ArrayList<>();
        // 查询数据库
        MojiJson ddindexJson = indexMapper.selectIndexDataById(cityId);
        //System.out.println(ddindexJson);
        // 判断时间是否是当天更新的---DateUtils.dateAmongMin(ddindexJson.getUpdatetime(), INDEX_UPDATE_TIME)
        if (ddindexJson != null && DateUtils.isToday(ddindexJson.getUpdatetime())) {
            // System.out.println("index>>>>>>>来自豆豆鸟");
            // 转换成集合
            indexs = JSONArray.parseArray(ddindexJson.getDataJson(), Index.class);
        } else {
            // System.out.println("index>>>>>>>来自墨迹");
            // 生活指数
            JSONObject dateJson = getMoJiJsonResult("index", cityId, INDEX_TOKEN, req).getJSONObject("data").getJSONObject("liveIndex");
            String indexJson = dateJson.getString(DateUtils.getDate());
            //判空？？？
            indexs = JSONObject.parseArray(indexJson, Index.class);
            // 除去息斯敏指数
            for (int i = 0; i < indexs.size(); i++) {
                Index index = indexs.get(i);
                if (index.getName().equals("息斯敏过敏指数")) {
                    indexs.remove(index);
                }
            }
            // 保存或者更新
            if (ddindexJson == null) {
                // 保存
                indexMapper.saveIndexJson(cityId, city, JSON.toJSONString(indexs), DateUtils.getDateYMDHMS());
            } else {
                indexMapper.updateIndexJson(cityId, JSON.toJSONString(indexs), DateUtils.getDateYMDHMS());
            }
        }

        // 处理空气质量指数---每天
        Air air = new Air("", "", "", "", "", "", "", "", "", "");
        // 查询数据库
        MojiJson ddairJson = airMapper.selectAirDataById(cityId);
        if (ddairJson != null && DateUtils.isToday(ddairJson.getUpdatetime())) {
            // System.out.println("air>>>>>来自豆豆鸟");
            air = JSONObject.parseObject(ddairJson.getDataJson(), Air.class);
        } else {
            // System.out.println("air>>>>>来自墨迹");
            String airJson = getMoJiJsonResult("aqi", cityId, AQI_TOKEN, req).getJSONObject("data").getString("aqi");
            //判断是否有数据
            if (airJson != null) {
                air = JSONObject.parseObject(airJson, Air.class);
            }
            // 保存或者更新
            if (ddairJson == null) {
                // 保存
                airMapper.saveAirJson(cityId, city, JSON.toJSONString(air), DateUtils.getDateYMDHMS());
            } else {
                airMapper.updateAirJson(cityId, JSON.toJSONString(air), DateUtils.getDateYMDHMS());
            }
        }

        // 实时天气数据
        Condition current = new Condition();
        // 查询
        MojiJson ddcurrentJson = currentMapper.selectCurrentDataById(cityId);
        if (ddcurrentJson != null && DateUtils.dateAmongMin(ddcurrentJson.getUpdatetime(), CURRENT_UPDATE_TIME)) {
            // System.out.println("current>>>>>>>来自豆豆鸟");
            current = JSONObject.parseObject(ddcurrentJson.getDataJson(), Condition.class);
        } else {
            // System.out.println("current>>>>>>>来自墨迹");
            // 发送请求获取墨迹数据
            JSONObject json = getMoJiJsonResult("condition", cityId, CONDITION_TOKEN, req);
            JSONObject jb = json.getJSONObject("data");
            JSONObject conditionJson = jb.getJSONObject("condition");
            String weather = conditionJson.getString("condition");
            String conditionId = conditionJson.getString("conditionId");
            String humidity = conditionJson.getString("humidity");
            String temp = conditionJson.getString("temp");// 气温
            String icon = conditionJson.getString("icon");// 图标
            String pressure = conditionJson.getString("pressure");// 气压
            String windspeed = conditionJson.getString("windSpeed");// 风速
            String winddirect = conditionJson.getString("windDir");// 风向
            String windpower = conditionJson.getString("windLevel");// 风级
            // 获取墨迹的时间戳
            // String update_time =
            // DateUtils.dateStr2Millis(conditionJson.getString("updatetime"));
            // 获取当前时间的时间戳
            String update_time = Long.toString(System.currentTimeMillis() / 1000);
            String realfeel = conditionJson.getString("realFeel");
            String uvi = conditionJson.getString("uvi");// 紫外线
            String tips = conditionJson.getString("tips");
            // 构造函数封装数据
            current = new Condition(weather, temp, icon, humidity, pressure, windspeed, winddirect, windpower, update_time, conditionId, realfeel, tips, uvi);
            // 保存或者更新
            if (ddcurrentJson == null) {
                // 保存
                currentMapper.saveCurrentJson(cityId, city, JSON.toJSONString(current), DateUtils.getDateYMDHMS());
            } else {
                // 更新
                currentMapper.updateCurrentJson(cityId, JSON.toJSONString(current), DateUtils.getDateYMDHMS());
            }
        }
        // 设置生活指数
        current.setIndexs(indexs);
        // 设置空气质量指数
        current.setAir(air);
        return current;
    }

    /**
     * 小时数据处理（hourly）---每小时
     *
     * @param cityId 城市id
     * @param city   城市名
     * @param req
     * @return
     */
    private Object hourlyControl(String cityId, String city, HttpServletRequest req) {
        // 查询数据库
        MojiJson ddhourlyJson = hourlyMapper.selectHourlyDataById(cityId);
        // 判断时间
        if (ddhourlyJson != null && DateUtils.dateAmongMin(ddhourlyJson.getUpdatetime(), HOURLY_UPDATE_TIME)) {
            // System.out.println("hourly>>>>>>>来自豆豆鸟");
            return JSON.parse(ddhourlyJson.getDataJson());
        } else {
            // System.out.println("hourly>>>>>>>来自墨迹");
            // 发送请求获取墨迹数据
            String mojiHourlyJson = getMoJiJsonResult("forecast24hours", cityId, FORECAST24HOURS_TOKEN, req).getJSONObject("data").getString("hourly");
            List<Hourly> hourlylist = JSONObject.parseArray(mojiHourlyJson, Hourly.class);

            // 保存或者更新
            if (ddhourlyJson == null) {
                hourlyMapper.saveHourlyJson(cityId, city, JSON.toJSONString(hourlylist), DateUtils.getDateYMDHMS());
            } else {
                hourlyMapper.updateHourlyJson(cityId, JSON.toJSONString(hourlylist), DateUtils.getDateYMDHMS());
            }
            return hourlylist == null ? "" : hourlylist;
        }
    }

    /**
     * 未来15天的数据和未来5天空气质量指数（AQI）处理---当天
     *
     * @param cityId
     * @param city
     * @param req
     * @return
     */
    private List<Forecast> forecast15daysControl(String cityId, String city, HttpServletRequest req) {
        // 查询数据库中的数据
        MojiJson forecast15Json = forecast15Mapper.selectForecast15DataById(cityId);
        // 判断时间,是当天更新的不？
        if (forecast15Json != null && DateUtils.isToday(forecast15Json.getUpdatetime())) {
            //System.out.println("forecast15>>>>>来自豆豆鸟"+DateUtils.isToday(forecast15Json.getUpdatetime()));
            return JSONArray.parseArray(forecast15Json.getDataJson(), Forecast.class);
        } else {
            //System.out.println("forecast15>>>>>来自墨迹"+DateUtils.isToday(forecast15Json.getUpdatetime()));
            // 未来15天---发送请求
            JSONObject forecast15 = getMoJiJsonResult("forecast15days", cityId, FORECAST15DAYS_TOKEN, req);
            // 未来15天---获取数据
            JSONArray forecastArray = forecast15.getJSONObject("data").getJSONArray("forecast");
            // 未来5天空气质量指数
            JSONObject aqiForecast = getMoJiJsonResult("aqiforecast5days", cityId, AQIFORECAST5DAYS_TOKEN, req);
            JSONArray aqiForecastArray = aqiForecast.getJSONObject("data").getJSONArray("aqiForecast");
            List<Forecast> forecasts = new ArrayList<>();
            if (forecastArray.size() > 0) {
                for (int i = 0; i < forecastArray.size(); i++) {
                    JSONObject forecastJson = forecastArray.getJSONObject(i);
                    String date = forecastJson.getString("predictDate");
                    String updatetime = DateUtils.dateStr2Millis(forecastJson.getString("updatetime"));
                    // 白天
                    String conditionDay = forecastJson.getString("conditionDay");
                    String imgDay = forecastJson.getString("conditionIdDay");
                    String sunrise = forecastJson.getString("sunrise");
                    String sunset = forecastJson.getString("sunset");
                    String tempDay = forecastJson.getString("tempDay");
                    String winddirectDay = forecastJson.getString("windDirDay");
                    String windpowerDay = forecastJson.getString("windLevelDay") + "级";
                    String windspeedDay = forecastJson.getString("windSpeedDay");
                    // 夜间
                    String conditionNight = forecastJson.getString("conditionNight");
                    String imgNight = forecastJson.getString("conditionIdNight");
                    String moonphase = forecastJson.getString("moonphase");
                    String moonrise = forecastJson.getString("moonrise");
                    String moonset = forecastJson.getString("moonset");
                    String tempNight = forecastJson.getString("tempNight");
                    String winddirectNight = forecastJson.getString("windDirNight");
                    String windpowerNight = forecastJson.getString("windLevelNight") + "级";
                    String windspeedNight = forecastJson.getString("windSpeedNight");

                    // 可能为null的要处理
                    AqiForecast aForecast = new AqiForecast("", "", "");
                    if (i < aqiForecastArray.size()) {
                        aForecast = aqiForecastArray.getObject(i, AqiForecast.class);
                        //判断日期是否相同
                        //System.out.println(aForecast.getDate());
                    }
                    // 创建对象封装数据
                    Forecast forecast = new Forecast(date, updatetime, conditionDay, imgDay, sunrise, sunset, tempDay,
                            winddirectDay, windpowerDay, windspeedDay, conditionNight, imgNight, moonphase,
                            moonrise, moonset, tempNight, winddirectNight, windpowerNight, windspeedNight, aForecast, null, null);
                    forecasts.add(forecast);
                }
            }

            // 判断是否有两天前的---墨迹没更新过来导致还有两天前的
            for (int j = 0; j < forecasts.size(); j++) {
                String dateTime = forecasts.get(j).getDate();
                // 如果超过两天则false
                if (!DateUtils.dateAmongDay(dateTime, 2)) {
                    forecasts.remove(j);
                }
            }

            // 保存或者更新
            if (forecast15Json == null) {
                // 保存
                forecast15Mapper.saveForecast15Json(cityId, city, JSON.toJSONString(forecasts), DateUtils.getDateYMDHMS());
            } else {
                // 更新
                forecast15Mapper.updateForecast15Json(cityId, JSON.toJSONString(forecasts), DateUtils.getDateYMDHMS());
            }
            return forecasts;
        }
    }

    /**
     * 限行数据处理---每天
     *
     * @param cityId
     * @param city
     * @param req
     * @return
     */
    public Object limitControl(String cityId, City city, HttpServletRequest req) {
        // 判断城市是否在限行城市中
        for (int i = 0; i < LIMIT_CITYS.length; i++) {
            String limitcity = LIMIT_CITYS[i];
            // 判断城市，city不为null并且name或者province包含limitcity，因为可能市中的某个区也是限行的：北京市朝阳区，兰州市城关区等
            if (city != null && (city.getName().contains(limitcity)) || city.getProvince().contains(limitcity)) {
                // 查询数据库
                MojiJson ddlimitJson = limitMapper.selectLimitDataById(cityId);

                // 判断是否为null和时间
                if (ddlimitJson != null && DateUtils.dateAmongMin(ddlimitJson.getUpdatetime(), LIMIT_UPDATE_TIME)) {
                    // System.out.println("limit>>>>>来自豆豆鸟");
                    // 返回数据库中的json
                    return JSON.parse(ddlimitJson.getDataJson());
                } else {
                    // System.out.println("limit>>>>>来自墨迹");
                    // 取墨迹的数据并保存数据库
                    // 限行数据
                    String mojiLimitJson = getMoJiJsonResult("limit", cityId, LIMIT_TOKEN, req).getJSONObject("data").getString("limit");
                    List<Limit> limitList = JSONObject.parseArray(mojiLimitJson, Limit.class);
                    // 判断是否保存还是更新
                    if (ddlimitJson == null) {
                        // 如果数据库没有则保存
                        limitMapper.saveLimitJson(cityId, city.getName(), JSON.toJSONString(limitList), DateUtils.getDateYMDHMS());
                    } else {
                        // 更新
                        limitMapper.updateLimitJson(cityId, JSON.toJSONString(limitList), DateUtils.getDateYMDHMS());
                    }
                    // 判断limitList是否为null，为null则设置为""
                    return limitList == null ? "" : limitList;
                }
            }
        }
        return "";
    }

    // 天气预警信息----当天，没有则保存为""
    private Object alertControl(String cityId, String city, HttpServletRequest req) {
        //先查询本地的数据
        MojiJson ddalertJson = alertMapper.selectAlertDataById(cityId);
        //判断:有天气预警并且是当天
        if (ddalertJson != null && !StringUtils.isEmpty(ddalertJson.getDataJson()) && DateUtils.isToday(ddalertJson.getUpdatetime())) {
            //返回本地数据库中数据
            return JSON.parse(ddalertJson.getDataJson());
        } else {
            //获取墨迹天气预警数据
            String mojiAlertJson = getMoJiJsonResult("alert", cityId, ALERT_TOKEN, req).getJSONObject("data").getString("alert");
            String jsonStr = "";
            List<Alert> alertlist = new ArrayList<>();
            //如果有数据的话则取出,否则保存""
            if (mojiAlertJson != null) {
                alertlist = JSONObject.parseArray(mojiAlertJson, Alert.class);
                jsonStr = JSON.toJSONString(alertlist);
            }

            //判断数据库中是否有数据
            if (ddalertJson == null) {
                //保存
                alertMapper.saveAlertJson(cityId, city, jsonStr, DateUtils.getDateYMDHMS());
            } else {
                //更新
                alertMapper.updateAlertJson(cityId, jsonStr, DateUtils.getDateYMDHMS());
            }
            return jsonStr;
        }
    }

}