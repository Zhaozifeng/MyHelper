package com.demo.health;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.SportItemAdapter;
import com.helper.adapter.SportItemAdapter.SportItemModel;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SportAdd extends Activity {
	
	public static String[] NAME = {"散步","整理家务","唱歌","走路","广播体操","健身操","跳舞"};
	public static float[]  VALUES = {3.2f, 3.1f, 3.3f, 3.5f, 5.5f, 5.6f, 5.6f};
	
	public ImageView imgMenu;
	public ListView  lv;
	public int curMonth;
	public int curDay;
	public int curYear;
	
	public ArrayList<SportItemModel>	List;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.sport_add);
		Utools.customTitle(this, "今天运动消耗");
		initUI();
	}
	
	
	public void initUI(){
		
		Calendar c = Calendar.getInstance();
		curMonth = c.get(Calendar.MONTH)+1;
		curDay   = c.get(Calendar.DAY_OF_MONTH);
		curYear  = c.get(Calendar.YEAR);
		
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setVisibility(View.INVISIBLE);
		lv = (ListView)findViewById(R.id.sport_add_lv);
		saveList();
		lv.setAdapter(new SportItemAdapter(this,List));		
		
		lv.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SportItemModel item = (SportItemModel)arg1.getTag();	
				showDialog(item);
			}			
		});		
	}
		
	
	/*
	 * 弹出对话框
	 */
	public void showDialog(final SportItemModel item){
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.sport_input_layout, null);
		final EditText edt = (EditText)view.findViewById(R.id.sport_input_edt);
		final TextView tv  = (TextView)view.findViewById(R.id.sport_input_tv); 
		
		//动态监听edittext
		edt.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {	
				String str = arg0.toString();
				if(!str.equals("")){					
					int minute = Integer.parseInt(str);	
					float kalo = (item.energy*minute)*1.00f;
					//四舍五入
					BigDecimal b = new BigDecimal(kalo);  
					float f1  	 =   b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
					tv.setText(f1+"千卡");
				}
				else{
					tv.setText(0+"千卡");
				}
				
			}			
		});
		
		Builder builder = new Builder(SportAdd.this);
		builder
		.setTitle(item.name)
		.setView(view)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				String str = edt.getText().toString();
				if(!"".equals(str))
					saveInDb(item,Integer.parseInt(str));
				else
					saveInDb(item,0);
			}
		})
		.setNegativeButton("取消", null);
		builder.show();
		
	}
	
	
	/*
	 * 保存到数据库
	 */	
	public void saveInDb(SportItemModel item,int min){
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		final ContentValues cv = new ContentValues();
		cv.put("name", item.name);
		cv.put("year", curYear);
		cv.put("month", curMonth);
		cv.put("day", curDay);
		cv.put("minute", min);
		cv.put("itemkalo", item.energy);
		
		//四舍五入
		float f = item.energy*min;
		BigDecimal   b   =   new   BigDecimal(f);  
		float f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
		
		cv.put("total",f1);
		long rec = sql.insert(MainDatabase.SPORT_TABLE_NAME, null, cv);
		if(rec==-1)
			Toast.makeText(SportAdd.this, "保存到数据出错", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(SportAdd.this, "保存到数据成功", Toast.LENGTH_LONG).show();

	}
	
	
	
	/*
	 * 初始化列表
	 */			
	public void saveList(){
		if(GlobalApp.getInstance().SportItemList==null){
			List = new ArrayList<SportItemModel>();
			for(int i=0;i<NAME.length;i++){
				SportItemModel item = new SportItemModel(NAME[i],VALUES[i],1);
				List.add(item);
			}
			GlobalApp.getInstance().SportItemList = List;
		}
		else{
			List = GlobalApp.getInstance().SportItemList;
		}
	}
}



















