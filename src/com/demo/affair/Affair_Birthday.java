package com.demo.affair;
import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.BirthListAdapter;
import com.helper.adapter.BirthListAdapter.BirthdayPersonModel;
import com.helper.adapter.CommonAdapter;
import com.helper.birthday.BirthAdd;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Affair_Birthday extends Activity {
	
	public ImageView		imgBack;
	public ImageView		imgMenu;
	public TextView			title;
	public TextView			topTv;
	public ListView			birthList;
	public ListView			menuList;
	public PopupWindow		menuWindow;
	public SQLiteDatabase 	mainDB;
	public Cursor			cursor;
	
	public String[]	menus	={"添加","查看","设置"};
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.activity_affair_birthday);
		Utools.customTitle(Affair_Birthday.this, getResources().getString(R.string.birthday));
		initUI(); 
	}
	
	
	public void initUI(){
		
		imgMenu		= (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				menuWindow.showAsDropDown(v);			
			}		
		});		
		topTv = (TextView)findViewById(R.id.birthday_top_tv);
		makePopWindow();
		makeList();
		
	}

	
	
	
	//列表
	public void makeList(){
		birthList	= (ListView)findViewById(R.id.birth_lv);
		mainDB = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		cursor = mainDB.query(MainDatabase.BIRTHDAY_TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		if(cursor.getCount()==0){
			topTv.setText("您还没有添加生日记录喔.....");
		}
		else
			birthList.setAdapter(new BirthListAdapter(Affair_Birthday.this,cursor));
		
		//点击查看
		birthList.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final BirthdayPersonModel person = (BirthdayPersonModel)arg1.getTag();
				GlobalApp.getInstance().selectBirthItem = (BirthdayPersonModel)arg1.getTag();	
				
				//当前生日提示框
				if(person.getOffsetdays()==0){
					Builder builder = new Builder(Affair_Birthday.this);
					builder
					.setTitle("生日提醒")
					.setMessage(person.name+"生日啦，发个短信祝福Ta一下吗？")
					.setPositiveButton("嗯，好的", new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							showSystemSms(person.wishtext);						
						}
					})
					.setNegativeButton("暂不发送", new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Affair_Birthday.this,BirthAdd.class);
							intent.putExtra(BirthAdd.BIRTH_PARAMS, true);
							startActivity(intent);							
						}
					});		
					builder.create().show();
				}
				
				else{
					Intent intent = new Intent(Affair_Birthday.this,BirthAdd.class);
					intent.putExtra(BirthAdd.BIRTH_PARAMS, true);
					startActivity(intent);
				}	
			}			
		});
		//长按删除
		birthList.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Utools.setVibrator(Affair_Birthday.this, 100, 1);
				BirthdayPersonModel person = (BirthdayPersonModel)arg1.getTag();
				showDeleteDialog(person);
				return false;
			}			
		});
	}
	
	/*
	 * 跳转到系统短信界面
	 */
	public void showSystemSms(String content){
		Uri smsTo = Uri.parse("smsto:");
		Intent intent = new Intent(Intent.ACTION_SENDTO,smsTo);
		if(!content.equals("")&&content!=null)
			intent.putExtra("sms_body", content);		
		startActivity(intent);
	}
	
	
	
	/*
	 * 删除对话框
	 */
	public void showDeleteDialog(final BirthdayPersonModel person){
		Builder builder = new Builder(Affair_Birthday.this);
		builder
		.setTitle("删除")
		.setMessage("是否要删除当前的联系人")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {	
				deletePerson(person);
				if(deletePerson(person)==0){
					Toast.makeText(Affair_Birthday.this, "删除成功", Toast.LENGTH_SHORT).show();
					GlobalApp.getInstance().BirhPeopleList = null;
					cursor = mainDB.query(MainDatabase.BIRTHDAY_TABLE_NAME, null, null, null, null, null, null);
					cursor.moveToFirst();
					birthList.setAdapter(new BirthListAdapter(Affair_Birthday.this,cursor));
					if(cursor.getCount()==0){
						topTv.setText("您还没有添加生日记录喔.....");
					}
				}
				else{
					Toast.makeText(Affair_Birthday.this, "删除失败", Toast.LENGTH_SHORT).show();
				}
			}
		})
		.setNegativeButton("取消", null);
		builder.create().show();		
	}
	/*
	 * 删除联系人数据库函数
	 */
	public int deletePerson(BirthdayPersonModel person){
		String condition = "year=? and month=? and day=? and nick_name=?";
		String[] params  = {person.year+"",person.month+"",person.day+"",person.name};
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase(); 
		return db.delete(MainDatabase.BIRTHDAY_TABLE_NAME, condition, params);
	}
	
	//下拉列表
	public void makePopWindow(){
		//菜单那列表
		LayoutInflater lf = LayoutInflater.from(Affair_Birthday.this);
		View view = lf.inflate(R.layout.account_menu_list2, null);
		menuList  = (ListView)view.findViewById(R.id.account_menu_title_list);
		menuList.setAdapter(new CommonAdapter(menus,Affair_Birthday.this));
		menuList.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0:
					Intent intent1 = new Intent(Affair_Birthday.this,BirthAdd.class);
					startActivity(intent1);
					break;
				}
			}			
		});		
		//要计算popup长宽
		int popWid 		= (int)(GlobalApp.getInstance().ScreenWidth*0.4);
		int popHeight	= (int)(GlobalApp.getInstance().ScreenHeight*0.6);					
		menuWindow = new PopupWindow(view, popWid, popHeight,true);
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	
}






















