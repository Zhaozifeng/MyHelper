package com.demo.object;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDatabase extends SQLiteOpenHelper {
	
	
	
	public static String DBASE_NAME       = "MainDatabase";
	public static String DIARY_TABLE_NAME = "Diary" ;
	public static String CREATE_DIARY_TABLE  = "create table "+DIARY_TABLE_NAME+
			" (_id integer primary key autoincrement, time text, title text, emotion text, emotion_id integer, content text )";
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













