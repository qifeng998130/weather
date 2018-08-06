package com.ddn.why.service.weather.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddn.why.domain.weather.HotCity;
import com.ddn.why.mapper.weather.HotCityMapper;
import com.ddn.why.service.weather.HotCityService;

@Service
public class HotCityServiceImpl implements HotCityService {

	@Autowired
	private HotCityMapper hotCityMapper;
	
	@Override
	public List<HotCity> seleHotCitieListService() {
		// TODO Auto-generated method stub
		return hotCityMapper.selectHotCityList();
	}

}
