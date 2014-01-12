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
		if(flag==1){
		vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(second);
		}
		if(flag==0){
		vibrator.cancel();
		}
	}

	//播放音频flag 1为播放，0为取消
	public static void setMedia(Context context,int flag){	
		if(flag==1){
			mediaplayer = MediaPlayer.create(context, R.raw.silent_cry);
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
		if(flag==0)
		{
			mediaplayer.stop();
			mediaplayer.release();
		}
	}

}























