package com.ddn.why.service.caiyun.impl;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ddn.why.domain.moji.AqiForecast;
import com.ddn.why.domain.moji.Forecast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddn.why.domain.moji.CaiyunJson;
import com.ddn.why.domain.moji.CaiyunForecastData;
import com.ddn.why.domain.moji.MojiJson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ddn.why.domain.moji.City;
import com.ddn.why.mapper.moji.CaiyunMapper;

import com.ddn.why.service.caiyun.CaiyunService;
import org.springframework.stereotype.Service;
import com.ddn.why.utils.HttpUtils;
import com.ddn.why.utils.DateUtils;
import java.text.SimpleDateFormat;
import com.ddn.why.mapper.moji.MoJIMapper;
import java.lang.Exception;

@Service
public class CaiyunServiceImpl implements CaiyunService {
    @Autowired
    private CaiyunMapper currentCaiyunMapper;
    @Autowired
    private MoJIMapper moJIMapper;

    public Map<String, String> getIconHourly(String icon, Float intensity) {
        String iconDay = "";
        String iconNight = "";
        String name = "";
        System.out.println("String icon "+icon);
        System.out.println("Float intensity "+intensity);
        Map<String, String> map = new HashMap<String, String>();

        if (icon.equals("CLEAR_DAY")) {
            iconDay = "0";
            iconNight = "30";
            name = "晴天";

        } else if (icon.equals("CLEAR_NIGHT")) {
            iconDay = "0";
            iconNight = "30";
            name = "晴夜";
        } else if (icon.equals("PARTLY_CLOUDY_DAY")) {
            iconDay = "1";
            iconNight = "31";
            name = "多云";
        } else if (icon.equals("PARTLY_CLOUDY_NIGHT")) {
            iconDay = "1";
            iconNight = "31";
            name = "多云";
        } else if (icon.equals("CLOUDY")) {
            iconDay = "2";
            iconNight = "2";
            name = "阴";
        } else if (icon.equals("RAIN")) {
            if (intensity >= 0 && intensity <= 0.9) {
                iconDay = "7";
                iconNight = "7";
                name = "小雨";
            } else if (intensity > 0.9 && intensity <= 2.87) {
                iconDay = "8";
                iconNight = "8";
                name = "中雨";
            } else if (intensity > 2.87) {
                iconDay = "9";
                iconNight = "9";
                name = "大雨";
            }

        } else if (icon.equals("SNOW")) {
            if (intensity >= 0 && intensity <= 0.9) {
                iconDay = "14";
                iconNight = "14";
                name = "小雪";
            } else if (intensity > 0.9 && intensity <= 2.87) {
                iconDay = "15";
                iconNight = "15";
                name = "中雪";
            } else if (intensity > 2.87) {
                iconDay = "16";
                iconNight = "16";
                name = "大雪";
            }
        } else if (icon.equals("WIND")) {
            iconDay = "0";
            iconNight = "30";
            name = "风";

        } else if (icon.equals("HAZE")) {
            iconDay = "20";
            iconNight = "20";
            name = "雾霾沙尘";
        }

        map.put("iconDay", iconDay);
        map.put("iconNight", iconNight);
        map.put("name", name);
        return map;
    }

    public Map<String, String> getIconValue(String icon, Date sunrise_date, Date sunset_date, Float intensity) {
        String value = "";
        String name = "";
        Map<String, String> map = new HashMap<String, String>();

        if (icon.equals("CLEAR_DAY")) {
            value = "0";
            name = "晴天";

        } else if (icon.equals("CLEAR_NIGHT")) {
            value = "30";
            name = "晴夜";
        } else if (icon.equals("PARTLY_CLOUDY_DAY")) {
            value = "1";
            name = "多云";
        } else if (icon.equals("PARTLY_CLOUDY_NIGHT")) {
            value = "31";
            name = "多云";
        } else if (icon.equals("CLOUDY")) {
            value = "2";
            name = "阴";
        } else if (icon.equals("RAIN")) {
            if (intensity >= 0 && intensity <= 0.9) {
                value = "7";
                name = "小雨";
            } else if (intensity > 0.9 && intensity <= 2.87) {
                value = "8";
                name = "中雨";
            } else if (intensity > 2.87) {
                value = "9";
                name = "大雨";
            }

        } else if (icon.equals("SNOW")) {
            if (intensity >= 0 && intensity <= 0.9) {
                value = "14";
                name = "小雪";
            } else if (intensity > 0.9 && intensity <= 2.87) {
                value = "15";
                name = "中雪";
            } else if (intensity > 2.87) {
                value = "16";
                name = "大雪";
            }

        } else if (icon.equals("WIND")) {

            Date nowDate = new Date();
            if (sunrise_date != null && sunset_date != null && nowDate.getTime() >= sunrise_date.getTime() && nowDate.getTime() <= sunset_date.getTime())//比较时间大小
            {
                value = "0";

            } else {

                value = "30";
            }
            name = "风";

        } else if (icon.equals("HAZE")) {
            value = "20";
            name = "雾霾沙尘";
        }
        map.put("value", value);
        map.put("name", name);
        return map;
    }
    //20180717 彩云 风速(km/h)转风级
    public String getWindLevel(double seed){
        String level = "";
        if (seed < 1){
            level = "0";
        }
        else if(1 <= seed && seed <= 6){
            level = "1";
        }
        else if(6< seed && seed <= 12){
            level = "2";
        }
        else if(12< seed && seed <= 20){
            level = "3";
        }
        else if(20< seed && seed <= 29){
            level = "4";
        }
        else if(29< seed && seed <= 39){
            level = "5";
        }
        else if(39< seed && seed <= 50){
            level = "6";
        }
        else if(50< seed && seed <= 62){
            level = "7";
        }
        else if(62< seed && seed <= 75){
            level = "8";
        }
        else if(75< seed && seed <= 89){
            level = "9";
        }
        else if(89< seed && seed <= 103){
            level = "10";
        }
        else if(103< seed && seed <=117){
            level = "11";
        }
        else if(117< seed && seed <= 134){
            level = "12";
        }
        else if(134< seed && seed <= 150){
            level = "13";
        }
        else if(150< seed && seed <= 167){
            level = "14";
        }
        else if(167< seed && seed <= 184){
            level = "15";
        }
        else if(184< seed && seed <= 202){
            level = "16";
        }
        else if(202< seed && seed <= 220){
            level = "17";
        } else {
            level = "17以上";
        }
        return level;
    }

    public String getWindDirection(Float direction) {
        String name = null;
        if (direction > 0 && direction < 90) {
            name = "东北风";
        } else if (direction > 90 && direction < 180) {
            name = "东南风";

        } else if (direction > 180 && direction < 270) {
            name = "西南风";

        } else if (direction > 270 && direction < 360) {
            name = "西北风";
        } else if (direction == 0) {
            name = "北风";
        } else if (direction == 90) {
            name = "东风";
        } else if (direction == 180) {
            name = "南风";
        } else if (direction == 270) {
            name = "西风";
        } else if (direction == 360) {
            name = "北风";
        }
        return name;
    }

    public Map<String, Object> getCaiyunAppResult(City city, String location) {

        Map<String, Object> map = new HashMap<String, Object>();

        CaiyunJson catiyunJson = currentCaiyunMapper.selectCaiyunDataById(city.getCityId());
        System.out.print(city.getCityId());
        if (catiyunJson != null && DateUtils.dateAmongMin(catiyunJson.getUpdatetime(), CURRENT_UPDATE_TIME)) {
            System.out.println("current>>>>>>>来自豆豆鸟"+catiyunJson.getHourlyJson());
            JSONObject conditionJson = JSONObject.parseObject(catiyunJson.getConditionJson());
            JSONArray hourly_array = JSONArray.parseArray(catiyunJson.getHourlyJson());
            JSONArray forecafsts_array = JSONArray.parseArray(catiyunJson.getForecastsJson());

            JSONArray alert_array = JSONArray.parseArray(catiyunJson.getAlertJson());

            map.put("condition", conditionJson);
            map.put("hourly", hourly_array);
            map.put("alert", alert_array);

            System.out.println("forecafsts_array>>>>>>>来自豆豆鸟"+forecafsts_array);
            map.put("forecasts", forecafsts_array);
            return map;
        } else {

            String caiyun_key = "YEja6ZStAz-wBzF6";
            if(location==null || "".equals(location)){
                location = city.getLocation();
                if(location == null || "".equals(location)){
                    JSONObject locationjson = getGaodeAddressCode(city.getProvince(), city.getName());
                    location = locationjson.getString("location");
                    if(location == null || "".equals(location)) {
                        return map;
                    }
                    moJIMapper.updateCityLocationById(city.getCityId(), location);

                }
            }

            String host = "https://api.caiyunapp.com";
            String path = "/v2/" + caiyun_key + "/" + location + "/weather.json";
            System.out.print(path);
            String method = "GET";
            Map<String, String> headers = new HashMap<String, String>();
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("dailysteps", "15");
            querys.put("hourlysteps", "130");
            querys.put("alert", "true");
            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                // 保存请求次数
                // 获取response的body
                String result = EntityUtils.toString(response.getEntity());
                JSONObject resultJson = JSONObject.parseObject(result);
                // status：1:正常请求 成功
                String code = resultJson.getString("status");
                System.out.print("catyun data: " +resultJson);
                if (code != null && code.equals("ok")) {
                    JSONObject res = resultJson.getJSONObject("result");
                    JSONObject hourly = res.getJSONObject("hourly");
                    JSONObject realtime = res.getJSONObject("realtime");
                    JSONObject daily = res.getJSONObject("daily");
                    JSONObject minutely = res.getJSONObject("minutely");
                    JSONObject alertJson = res.getJSONObject("alert");


                    JSONObject condition = new JSONObject();
                    JSONObject aqiJsonObj = new JSONObject();
                    // 15天气预报
                    List<Forecast> forecasts = new ArrayList<>();
                    List<JSONObject> alert_array = new ArrayList<>();
                    // 24天气预报
                    List<JSONObject> hourly_array = new ArrayList<>();

                    condition.put("conditionId", "1");
                    condition.put("humidity", Math.round(realtime.getFloatValue("humidity") * 100));
                    condition.put("pressure", Math.round(realtime.getFloatValue("pres") / 100));

                    JSONArray astro = daily.getJSONArray("astro");
                    Date sunrise_date = null;
                    Date sunset_date = null;
                    JSONObject precipitation_obj = realtime.getJSONObject("precipitation");
                    JSONObject local_obj = precipitation_obj.getJSONObject("local");
                    Float intensity = local_obj.getFloat("intensity");
                    if ((astro != null) && (astro.size() > 0)) {
                        for (int i = 0; i < astro.size(); i++) {
                            JSONObject astroJson = astro.getJSONObject(i);
                            String astro_date = astroJson.getString("date");
                            String curr_date = DateUtils.getDate();
                            if (curr_date.equals(astro_date)) {
                                String sunrise_string = astro_date + " " + astroJson.getJSONObject("sunrise").getString("time");
                                String sunset_string = astro_date + " " + astroJson.getJSONObject("sunset").getString("time");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                sunrise_date = sdf.parse(sunrise_string);
                                sunset_date = sdf.parse(sunset_string);
                                break;

                            }
                        }

                    }
                    Integer tmp = Math.round(realtime.getFloatValue("temperature"));
                    condition.put("temp", tmp);
                    Integer[] tmp_array = {tmp - 1, tmp, tmp + 1};
                    Integer index = (int) (Math.random() * tmp_array.length);
                    condition.put("realFeel", tmp_array[index]);
                    condition.put("tips", minutely.getString("description"));
                    condition.put("update_time", resultJson.getLongValue("server_time"));
                    JSONObject ultravioletjson = null;
                    JSONArray ultraviolet_array = daily.getJSONArray("ultraviolet");
                    if ((ultraviolet_array != null) && (ultraviolet_array.size() > 0)) {
                        ultravioletjson = (JSONObject) ultraviolet_array.get(0);
                        condition.put("uvi", ultravioletjson.getString("index"));
                    }

                    JSONObject real_wind = realtime.getJSONObject("wind");
                    Double windpower_speed = real_wind.getDouble("speed");

                    Double tran_windpower_speed = windpower_speed * 0.2777778;
                    String windpower = getWindLevel(windpower_speed);
                    String windDir = getWindDirection(real_wind.getFloat("direction"));
                    condition.put("winddirect", windDir);
                    condition.put("windpower", windpower);
                    condition.put("windspeed", String.format("%.2f", tran_windpower_speed));
                    JSONObject comfort = realtime.getJSONObject("comfort");
                    condition.put("cycomfort", comfort);

                    Map<String, String> icon = getIconValue(realtime.getString("skycon"), sunrise_date, sunset_date, intensity);

                    // condition 实时
                    aqiJsonObj.put("cityName", city.getName());
                    aqiJsonObj.put("pubtime", new Date().getTime());
                    aqiJsonObj.put("value", realtime.getString("aqi"));

                    aqiJsonObj.put("co", realtime.getString("co") + "mg/m³");
                    aqiJsonObj.put("no2", realtime.getString("no2") + "μg/m³");
                    aqiJsonObj.put("o3", realtime.getString("o3") + "μg/m³");
                    aqiJsonObj.put("pm10", realtime.getString("pm10") + "μg/m³");
                    aqiJsonObj.put("pm25", realtime.getString("pm25") + "μg/m³");
                    aqiJsonObj.put("rank", "");
                    aqiJsonObj.put("so2", realtime.getString("so2") + "μg/m³");
                    condition.put("air", aqiJsonObj);
                    condition.put("weather", icon.get("name"));
                    condition.put("icon", icon.get("value"));


                    map.put("condition", condition);


                    String hourly_24 = hourly.getString("status");
                    if (hourly_24 != null && hourly_24.equals("ok")) {
                        JSONArray hourly_skycon_array = hourly.getJSONArray("skycon");
                        JSONArray hourly_humidity = hourly.getJSONArray("humidity");
                        JSONArray hourly_pres = hourly.getJSONArray("pres");
                        JSONArray hourly_temperature = hourly.getJSONArray("temperature");
                        JSONArray hourly_windarray = hourly.getJSONArray("wind");
                        JSONArray hourly_precipitationarray = hourly.getJSONArray("precipitation");
//                    JSONArray hourly_aqiarray = hourly.getJSONArray("aqi");
//                    JSONArray hourly_pm25array = hourly.getJSONArray("pm25");
//                    String description = hourly.getString("description");

                        for (int i = 0; i < hourly_skycon_array.size(); i++) {
                            JSONObject hourlyJsonObj = new JSONObject();
                            JSONObject skycon_json = (JSONObject) hourly_skycon_array.get(i);
                            JSONObject humidityjson = (JSONObject) hourly_humidity.get(i);
                            JSONObject presjson = (JSONObject) hourly_pres.get(i);
                            JSONObject temperaturejson = (JSONObject) hourly_temperature.get(i);
                            JSONObject windjson = (JSONObject) hourly_windarray.get(i);
                            JSONObject precipitationajson = (JSONObject) hourly_precipitationarray.get(i);

                            Double hourly_windpower_speed = windjson.getDouble("speed");
                            Double hourly_tran_windpower_speed = hourly_windpower_speed * 0.2777778;
                            String hourly_windDir = getWindDirection(windjson.getFloat("direction"));
                            hourlyJsonObj.put("windDir", hourly_windDir);
                            hourlyJsonObj.put("windSpeed", String.format("%.2f", hourly_tran_windpower_speed));

                            String hourly_datetime = skycon_json.getString("datetime");
                            hourlyJsonObj.put("hour", hourly_datetime.substring(11, 13));

                            Integer humidity_int = Math.round(humidityjson.getFloatValue("value") * 100);
                            Integer pressure_int = Math.round(presjson.getFloatValue("value") / 100);
                            hourlyJsonObj.put("humidity", humidity_int.toString());
                            hourlyJsonObj.put("pressure", pressure_int.toString());

                            Integer tmp_val = Math.round(temperaturejson.getFloatValue("value"));
                            hourlyJsonObj.put("temp", tmp_val.toString());
                            Integer[] tmp_val_array = {tmp_val - 1, tmp_val, tmp_val + 1,};
                            Integer tmp_val_index = (int) (Math.random() * tmp_val_array.length);
                            hourlyJsonObj.put("realFeel", tmp_val_array[tmp_val_index].toString());

                            Map<String, String> icon_obj = getIconHourly(skycon_json.getString("value"), precipitationajson.getFloat("value"));

                            hourlyJsonObj.put("condition", icon_obj.get("name"));
                            hourlyJsonObj.put("iconDay", icon_obj.get("iconDay"));
                            hourlyJsonObj.put("iconNight", icon_obj.get("iconNight"));
                            if (ultravioletjson != null) {
                                hourlyJsonObj.put("uvi", ultravioletjson.getString("index"));
                            }
                            hourly_array.add(hourlyJsonObj);

                        }
                    }


                    map.put("hourly", hourly_array);

                    String daily_15 = daily.getString("status");
                    if (daily_15 != null && daily_15.equals("ok")) {
                        JSONArray daily_temperature_array = daily.getJSONArray("temperature");
                        JSONArray daily_skycon_array = daily.getJSONArray("skycon");
                        JSONArray daily_astro_array = daily.getJSONArray("astro");
                        JSONArray daily_aqi_array = daily.getJSONArray("aqi");
                        JSONArray daily_precipitation_array = daily.getJSONArray("precipitation");
//                    JSONArray daily_coldRisk_array = daily.getJSONArray("coldRisk");
//                    JSONArray daily_cloudrate_array = daily.getJSONArray("cloudrate");
//                    JSONArray daily_humidity_array = daily.getJSONArray("humidity");
//                    JSONArray daily_pres_array = daily.getJSONArray("pres");
//                    JSONArray daily_ultraviolet_array = daily.getJSONArray("ultraviolet");
//                    JSONArray daily_pm25_array = daily.getJSONArray("pm25");
                        JSONArray daily_carWashing_array = daily.getJSONArray("carWashing");
                        JSONArray daily_dressing_array = daily.getJSONArray("dressing");
                        JSONArray daily_wind_array = daily.getJSONArray("wind");
                        JSONArray imgNight_array = daily.getJSONArray("skycon_20h_32h");
                        JSONArray imgDay_array = daily.getJSONArray("skycon_08h_20h");

                        for (int i = 0; i < daily_temperature_array.size(); i++) {
                            JSONObject daily_temperature_json = daily_temperature_array.getJSONObject(i);
                            JSONObject daily_skycon_json = daily_skycon_array.getJSONObject(i);
                            JSONObject daily_astro_json = daily_astro_array.getJSONObject(i);
                            JSONObject daily_aqi_json = daily_aqi_array.getJSONObject(i);
//                        JSONObject daily_coldRisk_json = daily_coldRisk_array.getJSONObject(i);
//                        JSONObject daily_cloudrate_json = daily_cloudrate_array.getJSONObject(i);
//                        JSONObject daily_humidity_json = daily_humidity_array.getJSONObject(i);
//                        JSONObject daily_pres_json = daily_pres_array.getJSONObject(i);
//                        JSONObject daily_ultraviolet_json = daily_ultraviolet_array.getJSONObject(i);
//                        JSONObject daily_pm25_json = daily_pm25_array.getJSONObject(i);
                            JSONObject daily_dressing_json = daily_dressing_array.getJSONObject(i);
                            JSONObject daily_carWashing_json = daily_carWashing_array.getJSONObject(i);
                            JSONObject daily_precipitation_json = daily_precipitation_array.getJSONObject(i);
                            JSONObject imgNight_json = imgNight_array.getJSONObject(i);
                            JSONObject imgDay_json = imgDay_array.getJSONObject(i);

                            Float intensity_avg = daily_precipitation_json.getFloat("avg");

                            Map<String, String> imgNightobj = getIconHourly(imgNight_json.getString("value"), intensity_avg);
                            Map<String, String> imgDayobj = getIconHourly(imgDay_json.getString("value"), intensity_avg);


                            System.out.println("intensity_avg: " + intensity_avg);
                            System.out.println("imgDay_json: " + imgDay_json);
                            System.out.println("imgDayobj: " + imgDayobj);
                            JSONObject daily_wind_json = daily_wind_array.getJSONObject(i);
                            String date = daily_skycon_json.getString("date");

                            System.out.println("daily_skycon_json: " + daily_skycon_json);

                            String astro_date = daily_astro_json.getString("date");
                            JSONObject astro_sunset = daily_astro_json.getJSONObject("sunset");
                            JSONObject astro_sunrise = daily_astro_json.getJSONObject("sunrise");
                            String sunrise = astro_date + " " + astro_sunrise.getString("time") + ":00";
                            String sunset = astro_date + " " + astro_sunset.getString("time") + ":00";
                            Integer tempDay = Math.round(daily_temperature_json.getFloat("max"));
                            Integer tempNight = Math.round(daily_temperature_json.getFloat("min"));
                            Integer updatetime = resultJson.getIntValue("server_time");

                            Integer daily_aqi_avg = Math.round(daily_aqi_json.getFloat("avg"));
                            AqiForecast aForecast = new AqiForecast(daily_aqi_json.getString("date"), DateUtils.getDateYMDHMS(), daily_aqi_avg.toString());

                            JSONObject daily_avg_obj = daily_wind_json.getJSONObject("avg");
                            Double daily_avg_speed = daily_avg_obj.getDouble("speed");
                            Double windspeedDay = daily_avg_speed * 0.2777778;
                            String winddirectDay = getWindDirection(daily_avg_obj.getFloat("direction"));

                            String windpowerDay = getWindLevel(daily_avg_speed);
                            String windpowerNight = getWindLevel(daily_avg_speed);
                            String windspeedDay_string = String.format("%.2f", windspeedDay);

                            System.out.println("imgDayobj: " + imgDayobj);
                            System.out.println("imgDay_json: " + imgDay_json);
                            CaiyunForecastData daily_dressing_forecast = new CaiyunForecastData(daily_dressing_json.getString("index"),
                                    daily_dressing_json.getString("desc"), daily_dressing_json.getString("datetime"));
                            CaiyunForecastData daily_carWashing_forecast = new CaiyunForecastData(daily_carWashing_json.getString("index"),
                                    daily_carWashing_json.getString("desc"), daily_carWashing_json.getString("datetime"));
                            Forecast forecast = new Forecast(date, updatetime.toString(), imgDayobj.get("name"), imgDayobj.get("iconDay"), sunrise, sunset, tempDay.toString(),
                                    winddirectDay, windpowerDay + "级", windspeedDay_string, imgNightobj.get("name"), imgNightobj.get("iconNight"),
                                    "test", "2018-00-00 00:00:00", "2018-00-00 00:00:00",
                                    tempNight.toString(),
                                    winddirectDay, windpowerNight + "级", windspeedDay_string, aForecast,daily_dressing_forecast,daily_carWashing_forecast);
                            forecasts.add(forecast);

                        }

                    }

                    map.put("forecasts", forecasts);
                    String alert_status = alertJson.getString("status");
                    if (alert_status != null && alert_status.equals("ok")) {
                        Map<String, String> level_type_map = new HashMap<String, String>();
                        level_type_map.put("01", "台风");
                        level_type_map.put("02", "暴雨");
                        level_type_map.put("03", "暴雪");
                        level_type_map.put("04", "寒潮");
                        level_type_map.put("05", "大风");
                        level_type_map.put("06", "沙尘暴");
                        level_type_map.put("07", "高温");
                        level_type_map.put("08", "干旱");
                        level_type_map.put("09", "雷电");
                        level_type_map.put("10", "冰雹");
                        level_type_map.put("11", "霜冻");
                        level_type_map.put("12", "大雾");
                        level_type_map.put("13", "霾");
                        level_type_map.put("14", "道路结冰");
                        level_type_map.put("15", "森林火灾");
                        level_type_map.put("16", "雷雨大风");
                        Map<String, String> level_code_map = new HashMap<String, String>();
                        level_code_map.put("01", "蓝色");
                        level_code_map.put("02", "黄色");
                        level_code_map.put("03", "橙色");
                        level_code_map.put("04", "红色");

                        JSONArray content_alert = alertJson.getJSONArray("content");
                        for (int i = 0; i < content_alert.size(); i++) {
                            JSONObject alert_json_temp = content_alert.getJSONObject(i);
                            JSONObject add_alert = new JSONObject();
                            String level = alert_json_temp.getString("code");
                            String level_type_string = level_type_map.get(level.substring(0,2));
                            String level_name_string = level_code_map.get(level.substring(2));
                            System.out.print("content_alert "+level.substring(2));
                            System.out.print("content_alert "+level.substring(0,2));

                            String pubtimestamp= DateUtils.getTimeByMillis(alert_json_temp.getLongValue("pubtimestamp")*1000);
                            System.out.print("content_alert "+pubtimestamp);
                            add_alert.put("infoid", "");
                            add_alert.put("level", level_type_string);
                            add_alert.put("pub_time", pubtimestamp);
                            add_alert.put("name", level_name_string);
                            add_alert.put("title", alert_json_temp.getString("title"));
                            add_alert.put("type",  level_name_string+ level_type_string);
                            add_alert.put("content", alert_json_temp.getString("description"));
                            alert_array.add(add_alert);
                        }

                    }

                    map.put("alert", alert_array);
                    if (catiyunJson == null) {
                        // 保存
                        currentCaiyunMapper.saveCaiyunDataJson(city.getCityId(), city.getName(), JSON.toJSONString(condition),
                                JSON.toJSONString(hourly_array),JSON.toJSONString(forecasts),JSON.toJSONString(alert_array),
                                DateUtils.getDateYMDHMS());
                    } else {
                        // 更新
                        currentCaiyunMapper.updateCaiyunDataJson(city.getCityId(), JSON.toJSONString(condition),
                                JSON.toJSONString(hourly_array),JSON.toJSONString(forecasts),JSON.toJSONString(alert_array),
                                DateUtils.getDateYMDHMS());
                    }


                    // System.out.println("取到的json>>>>>"+json);
                } else {
                    // c:1:token异常 2:签名错误 其它：系统错误
                    String info = resultJson.getString("info");
                    System.out.println("code:" + code + ",状态c：" + info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    public JSONObject getGaodeAddressCode(String district, String ctiy_name) {
        JSONObject json = null;
        String gaodeAppID = "6c213e106b3cb72496414cdfdf34ba22";

//		String host = "http://restapi.amap.com/v3/geocode/geo";
//		String path = "/v3/geocode/geo?address="+ctiy_name+"&output=JSON&key=6c213e106b3cb72496414cdfdf34ba22";
//		String params = "key=" + gaodeAppID + "&address=" + ctiy_name;
//		String method = "GET";

        String host = "http://restapi.amap.com";
        String path = "/v3/geocode/geo";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("key", gaodeAppID);
        querys.put("address", district + ctiy_name);

        System.out.print(district + ctiy_name);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            // 保存请求次数
            // 获取response的body
            String result = EntityUtils.toString(response.getEntity());
            JSONObject resultJson = JSONObject.parseObject(result);
            // status：1:正常请求 成功

            System.out.print(resultJson);
            String code = resultJson.getString("status");
            if (code != null && code.equals("1")) {
                JSONArray geodecodes = resultJson.getJSONArray("geocodes");
                if ((geodecodes != null) && (geodecodes.size() > 0)) {
                    json = (JSONObject) geodecodes.get(0);
                }
                // System.out.println("取到的json>>>>>"+json);
            } else {
                // c:1:token异常 2:签名错误 其它：系统错误
                String info = resultJson.getString("info");
                System.out.println("code:" + code + ",状态c：" + info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}