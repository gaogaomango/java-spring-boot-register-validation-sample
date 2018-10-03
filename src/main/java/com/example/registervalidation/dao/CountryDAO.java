package com.example.registervalidation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.registervalidation.model.Country;

@Repository
public class CountryDAO {
	
	private static final Map<String, Country> COUNTRIES_MAP = new HashMap<>();
	
	static {
		initData();
	}
	
	private static void initData() {
		Country ja = new Country("JA", "Japan");
		Country vn = new Country("VN", "Vietnam");
        Country en = new Country("EN", "England");
        Country fr = new Country("FR", "France");
        Country us = new Country("US", "US");
        Country ru = new Country("RU", "Russia");
 
        COUNTRIES_MAP.put(ja.getCountryCode(), ja);
        COUNTRIES_MAP.put(vn.getCountryCode(), vn);
        COUNTRIES_MAP.put(en.getCountryCode(), en);
        COUNTRIES_MAP.put(fr.getCountryCode(), fr);
        COUNTRIES_MAP.put(us.getCountryCode(), us);
        COUNTRIES_MAP.put(ru.getCountryCode(), ru);
    }
	
	public Country findCountryByCode(String countryCode) {
		return COUNTRIES_MAP.get(countryCode);
	}
	
	public List<Country> getCountries() {
		List<Country> list = new ArrayList<>();
		list.addAll(COUNTRIES_MAP.values());
		return list;
	}
}
