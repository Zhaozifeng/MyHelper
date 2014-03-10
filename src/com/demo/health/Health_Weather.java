package com.demo.health;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.myhelper.R;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Health_Weather extends Activity {
	
	private ImageView imgBack;
	private ImageView imgReflesh;
	private TextView  tvTitle;	
	
	public TextView   curTemp;
	public TextView   curCity;
	public TextView	  curWind;
	public TextView	  curTips;
	public TextView   curDescripe;
	public TextView   Date;
	public TextView   curWeather;
	public TextView   UV;
	public TextView   XiChe;
	public TextView   LvYou;
	public TextView   ShuShi;
	public TextView   YunDong;
	public TextView   LiangShai;
	public TextView   GanMao;
	public ImageView  WeatherIcon;
	public GridView	  weatherGrid;
	
	public ProgressDialog loadDialog;	
	public JSONObject WeatherJson;
	
	//时间日期
	public Calendar calendar;
	public int year;
	public int month;
	public int day;
	public int weekday;
	
	public static int FORCAST_DAYS = 3;
	public Integer		picIDS[];
	public String		weatherStrs[];
	public String		weekdays[];
	
	public static String WEATHER_SAVE	= "weather_save";
	
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_health_weather);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initTitle();
		loadSharedPreferences();
		//alarmDialog();
	}
		
	
	/*
	 * 获取网络前提示
	 */
	public void alarmDialog(){
		Builder builder = new Builder(Health_Weather.this);
		builder
		.setTitle("开发者提醒")
		.setMessage("由于技术不成熟，您若是用2g网络将无法获取天气信息，请用3g或者WIFI链接网络，我们将进一步完善产品。")
		.setPositiveButton("现在就连", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				showProgressDialog("正在更新天气信息");
				WeatherConnectThread w = new WeatherConnectThread();
				w.start();
			}
		})
		.setNegativeButton("更改网络", null);
		builder.create().show();
	}
	
	
	/**
	 * 初始化标题栏
	 */	
	public void initTitle(){
		picIDS 		= new Integer[FORCAST_DAYS];
		weatherStrs = new String[FORCAST_DAYS];
		weekdays	= new String[FORCAST_DAYS];
		
		imgBack	   = (ImageView)findViewById(R.id.custom_title_rollback);
		imgReflesh = (ImageView)findViewById(R.id.custom_title_menu);
		tvTitle    = (TextView)findViewById(R.id.title_name);
		tvTitle.setText(getResources().getString(R.string.health_weather_icon));
		imgBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();				
			}			
		});
		imgReflesh.setBackgroundDrawable(getResources().getDrawable(R.drawable.weather_reflesh));
		imgReflesh.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				alarmDialog();
			}			
		});			
		curTemp    = (TextView)findViewById(R.id.weather_temperture_tv);
		curCity    = (TextView)findViewById(R.id.weather_city_tv);
		curWind	   = (TextView)findViewById(R.id.weather_wind_tv);
		curTips	   = (TextView)findViewById(R.id.weather_tips_tv);
		Date	   = (TextView)findViewById(R.id.weather_date_tv);
		curDescripe= (TextView)findViewById(R.id.weather_descripe_tv);
		XiChe      = (TextView)findViewById(R.id.weather_xc_tv);
		LvYou	   = (TextView)findViewById(R.id.weather_tr_tv);
		ShuShi	   = (TextView)findViewById(R.id.weather_co_tv);
		YunDong    = (TextView)findViewById(R.id.weather_cl_tv);
		LiangShai  = (TextView)findViewById(R.id.weather_ls_tv);
		GanMao     = (TextView)findViewById(R.id.weather_ag_tv);
		UV		   = (TextView)findViewById(R.id.weather_uv_tv);
		WeatherIcon	=(ImageView)findViewById(R.id.weather_img);
		
		//获取当天的日期
		calendar = Calendar.getInstance();
		year 	= calendar.get(Calendar.YEAR);
		month	= calendar.get(Calendar.MONTH)+1;
		day		= calendar.get(Calendar.DAY_OF_MONTH);
		weekday	= calendar.get(Calendar.DAY_OF_WEEK);			
		weatherGrid = (GridView)findViewById(R.id.weather_forecast_gv);				
	}
		
	/*
	 * 返回中文字星期
	 */
	public String weekdayCH(int wday){
		String str=null;
		switch(wday){
		case 1:
			str = "星期日";
			break;
		case 2:
			str = "星期一";
			break;
		case 3:
			str = "星期二";
			break;
		case 4:
			str = "星期三";
			break;
		case 5:
			str = "星期四";
			break;
		case 6:
			str = "星期五";
			break;
		case 7:
			str = "星期六";
			break;
		}
		return str;		
	}
		
	/**
	 * 展示天气数据信息
	 * @throws JSONException 
	 */
	public void initWeatherInfo(){
		String temp 	= null;
		String city 	= null;
		String weather  = null;
		String date 	= null;
		String wind 	= null;
		String xiche 	= null;
		String lvyou 	= null;
		String shushi	= null;
		String yundong	= null;
		String liangshai= null;
		String ganmao 	= null;
		String tips     = null;
		String uv		= null;
		int imgID		= 99;
		try {
			temp 	= WeatherJson.getString("temp1");
			city 	= WeatherJson.getString("city");
			weather = WeatherJson.getString("weather1");
			//date 	= WeatherJson.getString("date_y")+WeatherJson.getString("week");
			wind 	= WeatherJson.getString("wind1")+"，"+WeatherJson.getString("fl1");
			xiche	= "洗车 ："+WeatherJson.getString("index_xc");
			lvyou	= "旅游 ："+WeatherJson.getString("index_tr");
			shushi	= "舒适 ："+WeatherJson.getString("index_co");
			yundong = "运动 ："+WeatherJson.getString("index_cl");
			liangshai = "晾晒 ："+WeatherJson.getString("index_ls");
			ganmao	= "感冒 ："+WeatherJson.getString("index_ag");
			tips	= "温馨提示 ："+WeatherJson.getString("index_d");
			uv		= "紫外线强度为 ："+WeatherJson.getString("index_uv");
			//设置图片信息
			imgID = WeatherJson.getInt("img1");
			
			int j = 2;
			int k = weekday+1;
			int n = 3;
			//未来三天状况
			for(int i=0;i<FORCAST_DAYS;i++){
				picIDS[i] 		= WeatherJson.getInt("img"+n);
				weatherStrs[i]	= WeatherJson.getString("weather"+j);
				weekdays[i]		= weekdayCH(k);
				j++;	k++;	n+=2;
			}						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		curTemp.setText(temp);
		curCity.setText(city);
		curDescripe.setText(weather);
		//Date.setText(date);
		Date.setText(year+"年"+month+"月"+day+"日"+weekdayCH(weekday));
		curWind.setText(wind);
		XiChe.setText(xiche);
		LvYou.setText(lvyou);
		ShuShi.setText(shushi);
		YunDong.setText(yundong);
		LiangShai.setText(liangshai);
		GanMao.setText(ganmao);
		curTips.setText(tips);
		UV.setText(uv);
		
		//设置图片
		String pic_addr = "b"+imgID;
		int resId = getResources().getIdentifier(pic_addr, "drawable" ,this.getPackageName());
		WeatherIcon.setBackgroundResource(resId);	
		//未来三天天气情况
		weatherGrid.setAdapter(new WeatherAdapter());
		
		//每次更新完数据保存到本地
		SharedPreferences sp = this.getSharedPreferences(WEATHER_SAVE, MODE_PRIVATE);
		SharedPreferences.Editor	editor = sp.edit();
		editor.putString("city", city);
		editor.putString("temp", temp);
		editor.putString("weather", weather);
		editor.putInt("pic_id", imgID);
		editor.putString("wind", wind);
		editor.putString("uv", uv);
		editor.putString("car", xiche);
		
		editor.putString("travel", lvyou);
		editor.putString("comfort",shushi);
		editor.putString("sport", yundong);
		editor.putString("dry", liangshai);
		editor.putString("coach", ganmao);
		editor.putString("tips", tips);
		
		editor.commit();
	}
	
	
	/**
	 * 获取本地数据
	 * @param info
	 */
	public void loadSharedPreferences(){
		Date.setText(year+"年"+month+"月"+day+"日"+weekdayCH(weekday));
		SharedPreferences sp = this.getSharedPreferences(WEATHER_SAVE, MODE_PRIVATE);
		SharedPreferences.Editor	editor = sp.edit();
		if(sp!=null&&!sp.getString("temp", "").equals("")){
			curTemp.setText(sp.getString("temp", ""));
			curCity.setText(sp.getString("city", ""));
			curWind.setText(sp.getString("wind", ""));
			curTips.setText(sp.getString("tips", ""));
			//Date.setText(sp.getString("city", ""));
			curDescripe.setText(sp.getString("weather", ""));
			XiChe.setText(sp.getString("car", ""));
			LvYou.setText(sp.getString("travel", ""));
			ShuShi.setText(sp.getString("comfort", ""));
			YunDong.setText(sp.getString("sport", ""));
			LiangShai.setText(sp.getString("dry", ""));
			GanMao.setText(sp.getString("coach", ""));
			UV.setText(sp.getString("uv", ""));
			//设置图片
			String pic_addr = "b"+sp.getInt("pic_id", 99);
			int resId = getResources().getIdentifier(pic_addr, "drawable" ,this.getPackageName());
			WeatherIcon.setBackgroundResource(resId);
		}
		else
			alarmDialog();
	}
	
	
	
	
	/*
	 * 显示等待对话框
	 */	
	public void showProgressDialog(String info){
		loadDialog = new ProgressDialog(Health_Weather.this);
		loadDialog.setTitle("亲，请稍后");
		loadDialog.setMessage(info);
		loadDialog.setCanceledOnTouchOutside(true);
		loadDialog.show();
	}
	public void showResultDialog(String info){
		Builder builder = new Builder(Health_Weather.this);
		builder
		.setTitle("反馈信息")
		.setMessage(info)
		.setPositiveButton("嗯，我知道了", null);
		builder.create().show();
	}
	
	
	/**
	 * @author Administrator
	 *后天线程获取天气
	 */
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				loadDialog.dismiss();
				showResultDialog("更新数据成功");
				initWeatherInfo();
				break;
			case 1:
				showResultDialog("获取网路数据出错，请检查网络");
				break;				
			default:break;			
			}
		}
	};	
	private class WeatherConnectThread extends Thread{
		public void run(){
			try {			
				getJsonData();
				handler.sendEmptyMessage(0);
				
			} catch (Exception e) {
				loadDialog.dismiss();
				//showResultDialog("获取网路数据出错，请检查网络");
				handler.sendEmptyMessage(1);
			}
		}	
	}
	
	
	/**
	 * 获取中国天气气象台网站的接口，并返回数据json
	 * @throws Exception 
	 */
	public void getJsonData() throws Exception{
		String path = "http://m.weather.com.cn/data/101280101.html";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setReadTimeout(8000);
		conn.setConnectTimeout(8000);
		int code = conn.getResponseCode();				
		InputStream input = conn.getInputStream();
		byte[] data = readInputStream(input);
		String json = new String(data);
		JSONObject obj = new JSONObject(json);
		WeatherJson = (JSONObject)obj.get("weatherinfo");
	}

	/*
	 * 将数据转化为byte数组
	 */	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    } 
	
	
	private class WeatherAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return FORCAST_DAYS;
		}

		@Override
		public Object getItem(int arg0) {
			return weatherStrs[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater.from(Health_Weather.this);
			View view 	= inflater.inflate(R.layout.weather_item,null);
			TextView weekday = (TextView)view.findViewById(R.id.weather_item_week);
			TextView weather = (TextView)view.findViewById(R.id.weather_item_weather);
			ImageView weathericon = (ImageView)view.findViewById(R.id.weather_item_icon);
			weekday.setText(weekdays[arg0]);
			weather.setText(weatherStrs[arg0]);
			//设置图片
			if(picIDS[arg0]>=32){
				weathericon.setBackgroundDrawable
				(Health_Weather.this.getResources().getDrawable(R.drawable.weather_sample));
			}
			else{
				String pic_addr = "b"+picIDS[arg0];
				int resId = getResources().getIdentifier(pic_addr, "drawable" ,Health_Weather.this.getPackageName());
				weathericon.setBackgroundResource(resId);
			}		
			return view;
		}		
	}
	
	
	/**
	 * 保存天气数据
	 */
	public void saveWeatherData(){
		
	}

}

























