package com.helper.birthday;

import java.util.Calendar;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.R;
import com.demo.tools.Utools;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class BirthAdd extends Activity {
	
	public static String[] RELATION 	= {"家人","朋友","同学","同事","其他"};
	public static String[] STARS		= {"双鱼座","白羊座","金牛座","双子座","巨蟹座","狮子座",
		"处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座"};
	public ArrayAdapter<String>	adapterRealtion;
	public ArrayAdapter<String> adapterStars;
	
	public ImageView imgMenu;
	public ImageView imgMark;
	public ImageView imgSex;
	public Spinner		relationSpinner;
	public Spinner		starSpinner;
	public EditText		nickEdt;
	public EditText		faouriteEdt;
	public EditText		greetEdt;
	public EditText		timeEdt;
	public EditText		textEdt;
	public Button		timeBtn;
	public Button		starBtn;
	public CheckBox		textCheck;
	
			
	public boolean 		isMale   	= true;
	public boolean 		MaleMark 	= true; 
	public Calendar		calendar;
	public int			curYear;
	public int 			curMonth;
	public int 			curDay;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.birth_add_layout);
		Utools.customTitle(BirthAdd.this, getResources().getString(R.string.birthday_indiviual));
		initUI();
		setListener();
	}
	
	
	public void initUI(){		
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setBackgroundResource(R.drawable.btn_ok2);		
		imgMark = (ImageView)findViewById(R.id.birth_img_mark);
		imgSex	= (ImageView)findViewById(R.id.birth_sex_img);
		relationSpinner = (Spinner)findViewById(R.id.birth_relation_sp);
		starSpinner		= (Spinner)findViewById(R.id.birth_constellation_sp);
		timeBtn			= (Button)findViewById(R.id.birth_time_btn);
		starBtn			= (Button)findViewById(R.id.birth_constellation_btn);
		nickEdt			= (EditText)findViewById(R.id.birthday_nickname_edt);
		faouriteEdt		= (EditText)findViewById(R.id.birth_favourite_edt);
		timeEdt			= (EditText)findViewById(R.id.birth_time_edt);
		textEdt			= (EditText)findViewById(R.id.birth_wish_edt);
		textCheck		= (CheckBox)findViewById(R.id.birth_iswish_ck);
		adapterRealtion	= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,RELATION);
		adapterRealtion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterStars	= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,STARS);
		adapterStars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		relationSpinner.setAdapter(adapterRealtion);
		starSpinner.setAdapter(adapterStars);
		
		//设置当前时间
		calendar	=	Calendar.getInstance();
		curYear		=	calendar.get(Calendar.YEAR);
		curMonth	=	calendar.get(Calendar.MONDAY);
		curDay		=	calendar.get(Calendar.DAY_OF_MONTH);
		
		timeEdt.setText(curYear+"-"+(curMonth+1)+"-"+curDay);
		
		//设置当前月份属于的星座
		starSpinner.setSelection(Utools.getConstellationId((curMonth+1), curDay));
	}
	
	
	
	/*
	 * 监听函数
	 */
	public void setListener(){
		
		//性别图片
		imgSex.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				isMale = !isMale;
				if(isMale)
					imgSex.setBackgroundResource(R.drawable.male);
				else
					imgSex.setBackgroundResource(R.drawable.female);
			}			
		});
		
		//头像图片
		imgMark.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				MaleMark = !MaleMark;
				if(MaleMark)
					imgMark.setBackgroundResource(R.drawable.male_mark);
				else
					imgMark.setBackgroundResource(R.drawable.female_mark);
			}			
		});
		
		//时间按钮
		timeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				DatePickerDialog   dateDialog = new DatePickerDialog(BirthAdd.this,new OnDateSetListener(){
					public void onDateSet(DatePicker arg0, int arg1, int arg2,
							int arg3) {
						curYear 	= arg1;
						curMonth	= arg2;
						curDay		= arg3;
						timeEdt.setText(curYear+"-"+(curMonth+1)+"-"+curDay);
						int constellationid = Utools.getConstellationId((curMonth+1), arg3);
						starSpinner.setSelection(constellationid);
						Toast.makeText(BirthAdd.this, 
								BirthAdd.this.getResources()
								.getString(R.string.birthday_auto_constellation),
								8888).show();
					}					
				},curYear,curMonth,curDay);	
				dateDialog.show();
			}			
		});
		
		//开启短信服务功能
		textCheck.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
					textEdt.setEnabled(true);
				else
					textEdt.setEnabled(false);
			}			
		});
		
		
		
	}
}




















