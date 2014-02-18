package com.demo.health;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.demo.myhelper.R;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Health_Weather extends Activity {
	
	private ImageView imgBack;
	private ImageView imgReflesh;
	private TextView  tvTitle;	
	public ProgressDialog loadDialog;
	
	public JSONObject WeatherJson;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_health_weather);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initTitle();
		WeatherConnectThread w = new WeatherConnectThread();
		w.start();
	}
			
	/**
	 * 初始化标题栏
	 */	
	public void initTitle(){
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
				showProgressDialog("正在更新天气信息");	
				WeatherConnectThread w = new WeatherConnectThread();
				w.start();
			}			
		});	
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
				showResultDialog("获取网路数据出错，请检查网络");
			}
		}	
	}
	
	
	/**
	 * 获取中国天气气象台网站的接口，并返回数据json
	 * @throws Exception 
	 */
	public void getJsonData() throws Exception{
		String path = "http://m.weather.com.cn/data/101090507.html";
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

}

























