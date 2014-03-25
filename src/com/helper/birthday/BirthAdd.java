package com.helper.birthday;

import java.util.Calendar;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
	
	public static String CONSTELLATION = "constellation";
	
	public static String[] RELATION 	= {"家人","朋友","同学","同事","其他"};
	public static String[] STARS		= {"双鱼座","白羊座","金牛座","双子座","巨蟹座","狮子座",
		"处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座"};
	
	public ArrayAdapter<String>	adapterRealtion;
	public ArrayAdapter<String> adapterStars;
	
	public ImageView imgCommit;
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
		imgCommit = (ImageView)findViewById(R.id.custom_title_menu);
		imgCommit.setBackgroundResource(R.drawable.btn_ok2);		
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
								Toast.LENGTH_LONG).show();
					}					
				},curYear,curMonth,curDay);	
				dateDialog.show();
			}			
		});
		
		//查询星座性格
		starBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent intent = new Intent(BirthAdd.this,Constellation.class);
				int pos = starSpinner.getSelectedItemPosition();
				intent.putExtra(CONSTELLATION, pos);
				startActivity(intent);				
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
		
		//保存数据
		imgCommit.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Builder builder = new Builder(BirthAdd.this);
				builder
				.setTitle("提示")
				.setMessage("亲，是否提交数据")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						saveData();	
						finish();
					}
				})
				.setNegativeButton("取消", null);
				builder.create().show();				
			}			
		});			
	}
	
	
	/*
	 * 保存数据
	 */	
	public void saveData(){
		String name = nickEdt.getText().toString();
		if(name==null||"".equals(name)){
			Toast.makeText(BirthAdd.this, "昵称不能为空喔....", Toast.LENGTH_LONG).show();
			return;
		}
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		final ContentValues cv = new ContentValues();
		
		//头像
		if(MaleMark)
			cv.put("mark_id", 0);
		else
			cv.put("mark_id", 1);
		
		//关系
		cv.put("relation", relationSpinner.getSelectedItemPosition());
		
		//昵称
		cv.put("nick_name", nickEdt.getText().toString());
		
		//性别
		if(isMale)
			cv.put("sex_id", 0);
		else
			cv.put("sex_id", 1);
		
		//年月日
		cv.put("year", curYear);
		cv.put("month", curMonth);
		cv.put("day", curDay);
		
		//星座
		cv.put("constellation", starSpinner.getSelectedItemPosition());
		
		//喜爱的东西
		cv.put("favourite", faouriteEdt.getText().toString());
		
		//短信开启
		if(textCheck.isChecked()){
			cv.put("is_send", 0);
			cv.put("wish_text", textEdt.getText().toString());
		}
		else{
			cv.put("is_send", 1);
		}	
		sql.insert(MainDatabase.BIRTHDAY_TABLE_NAME, null, cv);
		Toast.makeText(BirthAdd.this, "提交成功", Toast.LENGTH_LONG).show();
		
	}	
}




















