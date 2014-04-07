package com.demo.affair;

import java.util.Calendar;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.BirthListAdapter;
import com.helper.adapter.SituationAdapter;
import com.helper.adapter.BirthListAdapter.BirthdayPersonModel;
import com.helper.adapter.SituationAdapter.SituationModel;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

//此类事情景模式转换的类
public class Affair_Mode extends Activity {
	
	public ImageView	imgAdd;		
	public ListView		listview;
	public Cursor		cursor;
	public EditText 	nameEdt;
	public CheckBox 	viCk;
	public SeekBar		volSeek;
	public SeekBar		lightSeek;
	public TimePicker 	tp;
	
	public String[] ITEMS = {"选择模式","预定设置","删除全部"};
	public String[] MODES = {"铃声+震动","铃声","震动","静音"};
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	
		setContentView(R.layout.activity_affair_mode);
		Utools.customTitle(Affair_Mode.this, "情景选择");
		initUI();
		showList();		
	}
	
	
	public void initUI(){
		imgAdd	 = (ImageView)findViewById(R.id.custom_title_menu);
		listview = (ListView)findViewById(R.id.mode_lv);
		imgAdd.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				//showAddDialog();		
				showListDialog();
			}			
		});		
		
		//单击列表选项
		listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SituationModel curMode = (SituationModel)arg1.getTag();	
				Toast.makeText(Affair_Mode.this, "长按删除自定义模式", Toast.LENGTH_SHORT).show();
			}			
		});
		
		//长按列表选项
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SituationModel curMode = (SituationModel)arg1.getTag();
				showDeleteDialog(curMode);
				return false;
			}			
		});	
	}	
	
	/*
	 * 选择列表
	 */
	public void showListDialog(){
		LayoutInflater inflater = LayoutInflater.from(Affair_Mode.this);
		View view = inflater.inflate(R.layout.mode_add_list, null);
		ListView lv = (ListView)view.findViewById(R.id.add_mode_lv);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ITEMS);
		lv.setAdapter(adapter);
		Builder builder = new Builder(this);
		builder.setSingleChoiceItems(ITEMS, TRIM_MEMORY_BACKGROUND,new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				
				case 0:
					showSetMode();
					break;
				case 1:
					showAddDialog();
					break;	
				case 2:
					deleteAll();
					break;
				}					
				dialog.dismiss();
			}
			});				
		builder.create().show();
	}
		
	
	/*
	 * 选择预定情景模式对话框
	 */
	public void showSetMode(){
		final AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		Builder builder = new Builder(this);
		builder.setSingleChoiceItems(MODES, TRIM_MEMORY_BACKGROUND, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
					Toast.makeText(Affair_Mode.this, "设置当前为"+MODES[which]+"模式", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
					Toast.makeText(Affair_Mode.this, "设置当前为"+MODES[which]+"模式", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
					Toast.makeText(Affair_Mode.this, "设置当前为"+MODES[which]+"模式", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
					am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
					Toast.makeText(Affair_Mode.this, "设置当前为"+MODES[which]+"模式", Toast.LENGTH_SHORT).show();
					break;
				
				}
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	/*
	 * 删除全部
	 */
	public void deleteAll(){		
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		Builder builder = new Builder(this);
		builder
		.setTitle("删除")
		.setMessage("您确认全部删除吗?")
		.setPositiveButton("恩，是的", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				int rec = sql.delete(MainDatabase.MODE_TABLE_NAME, null, null);
				if(rec!=0){
					Toast.makeText(Affair_Mode.this, "删除成功", Toast.LENGTH_SHORT).show();
					showList();		
				}
					
				else
					Toast.makeText(Affair_Mode.this, "删除失败", Toast.LENGTH_SHORT).show();					
			}
		})
		.setNegativeButton("取消", null);
		builder.create().show();	
	}
	
	/*
	 * 删除对话框
	 */
	public void showDeleteDialog(final SituationModel curMode){
		Utools.setVibrator(Affair_Mode.this, 100, 1);
		Builder builder = new Builder(Affair_Mode.this);
		builder
		.setTitle("删除")
		.setMessage("是否要删除当前的联系人")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {	
				String condition = "name=? and hour=? and minute=?";
				String[] valuse = {curMode.name,curMode.hour+"",curMode.minute+""};
				SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
				if(sql.delete(MainDatabase.MODE_TABLE_NAME, condition, valuse)!=0){
					Toast.makeText(Affair_Mode.this, "删除成功", Toast.LENGTH_SHORT).show();
					showList();					
				}
				else{
					Toast.makeText(Affair_Mode.this, "删除失败", Toast.LENGTH_SHORT).show();
				}
			}
		})
		.setNegativeButton("取消", null);
		builder.create().show();		
	}
	
	
	public void showAddDialog(){
		LayoutInflater inflater = LayoutInflater.from(Affair_Mode.this);
		final View view = inflater.inflate(R.layout.dia_situ_add, null);
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);		
		tp = (TimePicker)view.findViewById(R.id.situ_tp);
		tp.setIs24HourView(true);
		tp.setCurrentHour(hour);
		tp.setCurrentMinute(minute);
		
		nameEdt = (EditText)view.findViewById(R.id.dia_name);
		viCk 	= (CheckBox)view.findViewById(R.id.situ_vib_ck);
		volSeek = (SeekBar)view.findViewById(R.id.situ_vol_sb);
		lightSeek = (SeekBar)view.findViewById(R.id.situ_light_sb);
						
		//音量
		AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int Volumnmax = am.getStreamMaxVolume(AudioManager.STREAM_RING);
		volSeek.setMax(Volumnmax);		
		//屏幕最亮为1
		lightSeek.setMax(10);
			
		Builder builder = new Builder(Affair_Mode.this);
		builder
		.setTitle("添加")
		.setView(view)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				saveMode(view);				
			}
		});
		builder.create().show();	
	}
		
	
	//保存数据
	public void saveMode(View view){
			
		//名称		
		String namestr = nameEdt.getText().toString();
		
		//震动
		int isVir = 0;		
		if(viCk.isChecked()){
			isVir = 1;
		}
		
		//获取声音和亮度
		int 	curVol = volSeek.getProgress();
		float 	curLight = (float) (lightSeek.getProgress()/10.0);
		
		//时间
		int hour = tp.getCurrentHour();
		int minute = tp.getCurrentMinute();
		
		//保存
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		ContentValues cv = new ContentValues();		
		cv.put("name", namestr);
		cv.put("hour", hour);
		cv.put("minute", minute);
		cv.put("isvibrate", isVir);
		cv.put("volumn", curVol);
		cv.put("light", curLight);
		
		long result = sql.insert(MainDatabase.MODE_TABLE_NAME, null, cv);
		if(result+1!=0)
			Toast.makeText(Affair_Mode.this, "添加成功", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(Affair_Mode.this, "添加失败", Toast.LENGTH_SHORT).show();	
		showList();
	}

	
	/*
	 * 查询数据库并列表展示
	 */
	public void showList(){
		Cursor cursor;
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		cursor = sql.query(MainDatabase.MODE_TABLE_NAME, 
				null, null, null, null, null, null);		
		cursor.moveToFirst();
		listview.setAdapter(new SituationAdapter(Affair_Mode.this,cursor));
		
	}
	

}















