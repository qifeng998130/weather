package com.ddn.why.domain.moji;

public class CaiyunJson {

    private Integer cityid;
    private String city;
    private String cJson;// 数据json
    private String hJson;// 数据json
    private String fJson;// 数据json
    private String aJson;// 数据json
    private String createtime;
    private String updatetime;

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConditionJson() {
        return cJson;
    }

    public void setConditionJson(String conditionJson) {
        this.cJson = conditionJson;
    }


    public String getHourlyJson() {
        return hJson;
    }

    public void setHourlyJson(String hourlyJson) {
        this.hJson = hourlyJson;
    }


    public String getForecastsJson() {
        return fJson;
    }

    public void setForecastsJson(String forecastsJson) {
        this.fJson = forecastsJson;
    }



    public String getAlertJson() {
        return aJson;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
