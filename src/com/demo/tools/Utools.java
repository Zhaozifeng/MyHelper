package com.demo.tools;


//
//此类用做工具类，注意要写成静态函数的形式
import java.io.IOException;
import java.util.Calendar;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Utools extends Activity{
	
	private 	String mYear;
	private 	String mMonth;
	private   	String mDay;
	private		String mDayOfWeek;
	private     String mTime;
	private 	Calendar    mCalendar;
	private 	Context     mContext;
	public static MediaPlayer mediaplayer    =null;
	public static Vibrator    vibrator       =null;
		
	//设置时间字符串
	public void setDate(){                                                                                        
        mCalendar  = Calendar.getInstance();
        mYear      = mCalendar.get(Calendar.YEAR)+"";
        mMonth     = mCalendar.get(Calendar.MONTH)+1+"";
        mDay       = mCalendar.get(Calendar.DAY_OF_MONTH)+"";
        mDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)+"";                
	}
	
	//返回字符串
	public String getDate(){                                                                                
        return mYear+"-"+mMonth+"-"+mDay;
	}
	
	//删除数据库项目
	public static void deleteTableItem(String tableName,Cursor c,int position){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase(); 
	}
	
	//震动处理1为震动,0为停止
	public static void setVibrator(Context context,long second,int flag){
		vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
		if(flag==1){		
		vibrator.vibrate(second);
		}
		if(flag==0){
		vibrator.cancel();
		}
	}

	//播放音频flag 1为播放，0为取消
	public static void setMedia(Context context,int flag){	
		mediaplayer = MediaPlayer.create(context, R.raw.silent_cry);
		if(flag==1){			
			try {
				mediaplayer.prepare();
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				Toast.makeText(context, "播放音频异常，请检查是否存在该音频文件", 8000).show();
				e.printStackTrace();
			}	
			mediaplayer.start();
		}
		try{
			if(flag==0)
			{
				mediaplayer.stop();
				mediaplayer.release();
			}
		}catch(Exception e){
			return;
		}		
	}
	
	//普通定义标题栏
	public static void customTitle(final Activity activity,String title){
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_layout);
		ImageView 	back = (ImageView)activity.findViewById(R.id.custom_title_rollback);
		back.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				activity.finish();				
			}			
		});
		TextView	customTitle = (TextView)activity.findViewById(R.id.title_name);
		customTitle.setText(title);		
	}
	
	
	
	//计算星座
	public static int getConstellationId(int m,int d){
		if((m==3&&d>=21)||(m==4&&d<=20))
			return 1;		//白羊
		else if((m==4&&d>=21)||(m==5&&d<=20))
			return 2;		//金牛
		else if((m==5&&d>=21)||(m==6&&d<=21))
			return 3;		//双子
		else if((m==6&&d>=22)||(m==7&&d<=22))
			return 4;		//巨蟹
		else if((m==7&&d>=23)||(m==8&&d<=22))
			return 5;		//狮子
		else if((m==8&&d>=23)||(m==9&&d<=22))
			return 6;		//处女
		else if((m==9&&d>=23)||(m==10&&d<=23))
			return 7;		//天秤
		else if((m==10&&d>=23)||(m==11&&d<=22))
			return 8;		//天蝎
		else if((m==11&&d>=23)||(m==12&&d<=21))
			return 9;		//人马
		else if((m==12&&d>=22)||(m==1&&d<=19))
			return 10;		//摩羯
		else if((m==1&&d>=20)||(m==2&&d<=18))
			return 11;		//水瓶
		else if((m==3&&d>=19)||(m==4&&d<=30))
			return 0;		//双鱼
		
		else return -1;
		
		
	}

}























