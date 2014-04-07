package com.demo.affair;

import java.util.ArrayList;
import java.util.Calendar;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;
import com.demo.tools.Utools;
import com.helper.adapter.FestivalAdapter;
import com.helper.adapter.FestivalAdapter.FestivalModel;
import com.helper.festival.FestivalIntroduction;
import com.helper.festival.MainFestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Affair_Festival extends Activity {
	
	public ImageView menuImg;
	public ListView	 listview;
	public TextView  nameTv;
	public TextView	 offsetdayTv;
	public Spinner	 monthSp;
	
	public String[] MONTHS = {"1","2","3","4","5","6","7","8"
								,"9","10","11","12"};
	
	public ArrayList<FestivalModel> list = new ArrayList<FestivalModel>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_festival);
		Utools.customTitle(Affair_Festival.this, "节日港");
		initUI();
	}
	
	
	
	public void initUI(){		
		//初始化数据
		if(MainFestival.festivalList.isEmpty())
			MainFestival.setList();		
		listview  = (ListView)findViewById(R.id.festival_lv);
		menuImg = (ImageView)findViewById(R.id.custom_title_menu);
		monthSp = (Spinner)findViewById(R.id.festival_sp);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(this,android.R.layout.simple_dropdown_item_1line,MONTHS);
		monthSp.setAdapter(adapter);
			
		menuImg.setVisibility(View.INVISIBLE);	
		Calendar calendar = Calendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH);
		monthSp.setSelection(curMonth);
		

		//节日列表选项
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GlobalApp.getInstance().selectFestival = (FestivalModel)arg1.getTag();
				Intent intent = new Intent(Affair_Festival.this,FestivalIntroduction.class);
				startActivity(intent);
			}			
		});
		
		
		//下拉表选项
		monthSp.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				int selectmonth = Integer.parseInt(MONTHS[arg2]);
				makeList(selectmonth);
				Toast.makeText(Affair_Festival.this, "节日日期只限于2014年", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}			
		});
		
	}
	
	
	
	
	
	public void makeList(int month){
		
		//搜索当前节日
		list.clear();
		for(int i=0;i<GlobalApp.getInstance().festivalList.size();i++){
			FestivalModel fm = GlobalApp.getInstance().festivalList.get(i);
			if(fm.month==month)
				list.add(fm);
		}		
		//列表展示
		listview.setAdapter(new FestivalAdapter(Affair_Festival.this,list));
		
	}
	

}
























