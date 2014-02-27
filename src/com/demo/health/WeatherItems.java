package com.demo.health;

/*
 * 保存天气对象
 */
public class WeatherItems {
	
	private String weekday;	
	private String weather;
	private String temperture;
	private int		picId;
	
	public WeatherItems(String weekday,String weather,String temp,int id){
		this.weekday 	= weekday;
		this.weather 	= weather;
		this.temperture = temp;
		this.picId 		= id;
	}
}
