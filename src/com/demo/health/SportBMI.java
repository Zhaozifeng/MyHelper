package com.demo.health;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.myhelper.R;

public class SportBMI extends Activity  {
	
	public static String THIN = "嘻嘻，您是个典型的瘦子，体质偏瘦，建议多摄入各种营养，增加体重。做一个好胖纸。";
	public static String NORMAL = "您的体型很标准，平时有很好的合理饮食和运动，请继续保持";
	public static String LITTLE_FAT = "您有点胖了，不过没有关系，注意增加运动量就好了";
	public static String FAT = "嘻嘻，您是个典型的大胖纸，也许您是个吃货，但注意饮食结构，并坚持有氧运动，这样对您的身体有好处";
	
	public TextView 	BMITitle;
	public ImageView 	imgBack;
	public ImageView 	imgMenu;
	public EditText 	BMIHeight;
	public EditText 	BMIWeight;
	public EditText 	BMIValue;
	public Button 		BMISubmit;
	public Button 		BMIExplain;
	public TextView 	adviceTv;
	
	
	public float Height;
	public float Weight;
	public String Value;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.sport_bmi);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout_bmi);
		
		setTitle();
		
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		BMIHeight 	= (EditText)findViewById(R.id.health_bmi_height_edt);
		BMIWeight 	= (EditText)findViewById(R.id.health_bmi_weight_edt);
		BMIValue 	= (EditText)findViewById(R.id.health_bmi_value_show);
		BMISubmit 	= (Button)findViewById(R.id.health_bmi_sub);
		BMIExplain 	= (Button)findViewById(R.id.health_bmi_btn);
		adviceTv    = (TextView)findViewById(R.id.health_bmi_tv);
		
		calculateBMI();		
		showBMICalore();
	}
	
	public void setTitle(){
		BMITitle = (TextView)findViewById(R.id.title_bmi_name);
		BMITitle.setText(R.string.health_bmi_title);
		imgBack	= (ImageView)findViewById(R.id.custom_title_rollback);
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setVisibility(View.INVISIBLE);
		imgBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();				
			}			
		});		
	}
	
	public void calculateBMI(){
		BMISubmit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				String str = "";
				float val;
				str = BMIHeight.getText().toString();
				if("".equals(str))
					Toast.makeText(SportBMI.this, "身高或者体重值不能为空喔", Toast.LENGTH_SHORT).show();
				else{
					Height = (Float.parseFloat(str));
					str = BMIWeight.getText().toString();
					Weight = Float.parseFloat(str);
					val = (float)Math.round(Weight*1000000/(Height*Height))/100;
					analyseValue(val); 	
				}	
			}
		});
	}
	
	public void showBMICalore(){
		BMIExplain.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showBMIDialog();
			}
		});
	}
	
	
	/*
	 * BMI百科对话框
	 */
	public void showBMIDialog(){
		String intrduce = getResources().getString(R.string.bmi_calorie);
		Builder builder = new Builder(SportBMI.this);
		builder
		.setTitle("关于BMI介绍")
		.setMessage(intrduce)
		.setPositiveButton("恩，知道了", null);
		builder.create().show();
		
	}
	
	
	/*
	 * 标准值的判断
	 */
	public void analyseValue(float val){
		
		BMIValue.setText(val+"");
		if(val <= 18.5){	
			adviceTv.setText(THIN);
		}
		else if((val > 18.5) &&(val <= 25)){
			adviceTv.setText(NORMAL);
		}
		else if((val > 25) &&(val <= 28)){
			adviceTv.setText(LITTLE_FAT);
		}
		else if(val > 28){
			adviceTv.setText(FAT);
		}
	}
	
	
}











