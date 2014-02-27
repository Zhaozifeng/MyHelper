package com.demo.health;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

public class WeatherManager {
	
	public int FORECAST_DAYS = 3;	
	public Context 		context;
	public JSONObject 	weatherJSON;
	public List<WeatherItems>	weatherlist;
	
	public WeatherManager(Context context,JSONObject json){
		this.context 	 = context;
		this.weatherJSON = json;
		weatherlist = new ArrayList<WeatherItems>();
		for(int i=0;i<FORECAST_DAYS;i++){
			try {
				String weekday = weatherJSON.getString("week");
				String weather = weatherJSON.getString("weather1");
				String temp	   = weatherJSON.getString("temp1");
				int id		   = weatherJSON.getInt("img1");				
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}	
	}
	
	
	

}
