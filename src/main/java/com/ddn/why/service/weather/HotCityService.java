package com.ddn.why.service.weather;

import java.util.List;

import com.ddn.why.domain.weather.HotCity;

//热门城市业务接口
public interface HotCityService {

	List<HotCity> seleHotCitieListService();
	
}
