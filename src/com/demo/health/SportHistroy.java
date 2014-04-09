package com.demo.health;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.SportHistroyAdapter;
import com.helper.adapter.SportHistroyAdapter.SportDateModel;

public class SportHistroy extends Activity {
	
	public String[] MONTHS = {"1","2","3","4","5","6","7","8"
			,"9","10","11","12"};
	
	public ImageView 	imgAdd;
	public Spinner 		monSp;
	public TextView		totalTv;
	public ListView     lv;
	public int			month;
	public int 			day;
	public int 			year;
	
	public ArrayList<SportDateModel> list = new ArrayList<SportDateModel>();
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.sport_histroy);
		Utools.customTitle(this, "历史记录");
		initUi();
	}
			
	public void initUi(){
		Calendar c 	= Calendar.getInstance();
		year 		= c.get(Calendar.YEAR);
		month 		= c.get(Calendar.MONTH);
		day 		= c.get(Calendar.DAY_OF_MONTH); 
				
		imgAdd = (ImageView)findViewById(R.id.custom_title_menu);
		imgAdd.setBackgroundResource(R.drawable.add_selector);		
		imgAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SportHistroy.this,SportAdd.class);
				startActivity(intent);
				finish();				
			}			
		});
		
		lv = (ListView)findViewById(R.id.sport_histroy_lv);
		
		monSp 	= (Spinner)findViewById(R.id.histroy_sp);
		totalTv = (TextView)findViewById(R.id.histroy_totla_tv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(this,android.R.layout.simple_dropdown_item_1line,MONTHS);
		monSp.setAdapter(adapter);
		monSp.setSelection(month);
		monSp.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				refleshList();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}			
		});	
		
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(SportHistroy.this, "长按选项删除", Toast.LENGTH_SHORT).show();			
			}			
		});
		
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Utools.setVibrator(SportHistroy.this, 100, 1);
				SportDateModel item = (SportDateModel)arg1.getTag();
				showCancelDialog(item);
				return false;
			}			
		});
	}
	
	
	
	/*
	 * 删除对话框
	 */
	public void showCancelDialog(final SportDateModel item){
		Builder builder = new Builder(SportHistroy.this);
		builder
		.setTitle("删除")
		.setMessage("您是否删除“"+item.name+"”记录?")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteInDb(item);			
			}
		})
		.setNegativeButton("取消", null);
		builder.create().show();
	}
	
	/*
	 * 从数据库中删除
	 */
	public void deleteInDb(SportDateModel item){
		String condition = "name=? and year=? and month=? and day=? and minute=? and random=?";
		String[] values  = {item.name,item.year+"",item.month+"",item.day+"",item.minute+"",item.random+""};
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		int rec = sql.delete(MainDatabase.SPORT_TABLE_NAME, condition, values);
		if(rec!=0){
			Toast.makeText(SportHistroy.this, "删除成功", Toast.LENGTH_LONG).show();
			refleshList();
		}
		else
			Toast.makeText(SportHistroy.this, "删除失败", Toast.LENGTH_LONG).show();			
	}
	
	
	/*
	 * 刷新lv
	 */
	public void refleshList(){
		list.clear();
		serachBd();		
		SportHistroyAdapter adapter = new SportHistroyAdapter(SportHistroy.this,list);
		lv.setAdapter(adapter);
		float sum = SportHistroyAdapter.getSumKalu(list);
		totalTv.setText(sum+"千卡");
	}
	
	
	/*
	 * 获取数据库
	 */
	public void serachBd(){
		
		int curm = monSp.getSelectedItemPosition()+1;
		String condition = "year=? and month=? ";
		String[] values  = {year+"",curm+"",};
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		Cursor cursor = sql.query
		(MainDatabase.SPORT_TABLE_NAME, null, condition, values, null, null, null);
		cursor.moveToFirst();		
		if(cursor.getCount()==0)
			Toast.makeText(SportHistroy.this, "亲，没有相关记录喔", Toast.LENGTH_LONG).show();
		else{
			
			int size = cursor.getCount();
			for(int i=0;i<size;i++){
				String name = cursor.getString(1);
				int year = cursor.getInt(2);
				int month = cursor.getInt(3);
				int day = cursor.getInt(4);
				int minute = cursor.getInt(5);
				float total = cursor.getFloat(7);
				int rand = cursor.getInt(8);
				SportDateModel item = new SportDateModel
				(name,year,month,day,minute,total,rand);
				list.add(item);
				cursor.moveToNext();
			}
			
		}
	}
	

}









