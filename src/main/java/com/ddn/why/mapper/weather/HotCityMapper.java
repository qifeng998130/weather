package com.ddn.why.mapper.weather;

import java.util.List;

import com.ddn.why.domain.weather.HotCity;

public interface HotCityMapper {

	List<HotCity> selectHotCityList();
	
}
