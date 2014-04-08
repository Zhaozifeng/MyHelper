package com.demo.object;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDatabase extends SQLiteOpenHelper {
	
	
	
	public static String DBASE_NAME         = "MainDatabase";
	public static String DIARY_TABLE_NAME   = "Diary" ;
	public static String ECONOMY_TABLE_NAME = "Economy";
	public static String NOTE_TABLE_NAME    = "Note";
	public static String BIRTHDAY_TABLE_NAME = "Birthday";
	public static String MODE_TABLE_NAME	 = "Mode";
	public static String FESTIVAL_TABLE_NAME = "Festival";
	public static String SPORT_TABLE_NAME	 = "Sport";
	
	//建立备忘录表sql语句
	public static String CREATE_NOTE_TABLE = "create table "+NOTE_TABLE_NAME+					
			"(_id integer primary key autoincrement,year integer,month integer,day integer,hour integer,"
			+ "minute integer,second integer,content text,rang integer,vibrate integer,is_action text)";
	
	//建立日记表sql语句		
    public static String CREATE_DIARY_TABLE  = "create table "+DIARY_TABLE_NAME+				
			" (_id integer primary key autoincrement, time text, title text, emotion text, emotion_id integer, content text )";
    
    //建立经济账本sql语句
	public static String CREATE_ECONOMY_TABLE = "create table "+ECONOMY_TABLE_NAME+			   
			" (_id integer primary key autoincrement, kind text, sort text,fee money, year_int integer, month_int integer," +
			" date text, time text, content text)";
	
	//人物资料的数据库
	public static String CREATE_BIRTHDAY_TABLE = "create table "+BIRTHDAY_TABLE_NAME+
			"(_id integer primary key autoincrement, mark_id integer, relation integer, nick_name text, " +
			"sex_id integer, year integer, month integer, day integer, constellation integer, favourite text," +
			"is_send integer, wish_text text)";
	
	//情景模式数据库
	public static String CREATE_MODE_TABLE = "create table "+MODE_TABLE_NAME+"(_id integer primary key autoincrement," +
			"name text, hour integer, minute integer, isvibrate integer, volumn integer, light float,isopen ingeter)";
	
	//节日数据表，记录提醒的
	public static String CREATE_FESTIVAL_TABLE = "create table "+FESTIVAL_TABLE_NAME+"(_id integer primary key autoincrement," +
			"name text, month integer, day integer, alarm integer)";
	
	//运动数据表
	public static String CREATE_SPORT_TABLE = "create table "+SPORT_TABLE_NAME+"(_id integer primary key autoincrement," +
			"name text,year integer, month integer, day integer, minute integer, itemkalo float, total float)";
	
	public static List<Obejct_Diary> listofDiary = new ArrayList<Obejct_Diary>();
	
	public static SQLiteDatabase  MainSQLite;
	Context mainContext;
	
	public MainDatabase(Context c, int version){				//主数据库的构造函数
		super(c, DBASE_NAME, null, version);
		this.mainContext = c;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DIARY_TABLE);					       //创建日记表
		db.execSQL(CREATE_ECONOMY_TABLE);					   //创建经济表
		db.execSQL(CREATE_NOTE_TABLE); 						   //创建备忘录表
		db.execSQL(CREATE_BIRTHDAY_TABLE);					   //创建生日表
		db.execSQL(CREATE_MODE_TABLE);						   //创建情景模式表
		db.execSQL(CREATE_FESTIVAL_TABLE);					   //创建节日模式列表
		db.execSQL(CREATE_SPORT_TABLE);						   //创建运动列表
		MainSQLite = db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}
	
	public SQLiteDatabase getMainSQLite(){
		return this.MainSQLite;
	}

}













