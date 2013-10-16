package com.demo.object;

//这是备忘录对象类，作为数据存进数据库

public class Object_Note {
	
	public static boolean OPEN_SOUND  = true;
	public static boolean CLOSE_SOUND = false;
	public static boolean OPEN_SHAKE  = true;
	public static boolean CLOSE_SHAKE = false;
	public String 	NoteContent;
	public String 	When;
	public int 	 	NoteYear;
	public int 	  	NoteMonth;
	public int    	NoteDay;
	public int	  	NoteHour;
	public int    	NoteMinute;
	public boolean  NoteSound;
	public boolean  NoteShake;
	
	public Object_Note(int hour,int minute,String when){			  //时分构造函数
		this.NoteHour   = hour;
		this.NoteMinute = minute;
		this.When       = when;
	}
		
	public Object_Note(int year,int month,int day){					  //年月日构造函数
		this.NoteYear  = year;
		this.NoteMonth = month;
		this.NoteDay   = day;		
	}
		
	public void setNoteContent(String NoteContent){					  //设置备忘内容
		this.NoteContent = NoteContent;
	}
	
	public void setSound(boolean NoteSound){						  //设置声音
		this.NoteSound = NoteSound;
	}
	
	public void setNoteShake(boolean NoteShake){					  //设置震动
		this.NoteShake = NoteShake;
	}
	
	public int getYear(){
		return this.NoteYear;
	}
	
	public int getMonth(){
		return this.NoteMonth;
	}
	
	public int getDay(){
		return this.NoteDay;
	}
	
	public int getHour(){
		return this.NoteHour;
	}

	public int getMinute(){
		return this.NoteMinute;
	}
	
	public String getWhen(){
		return this.When;
	}

	public boolean getSound(){								//获取是否选中声音
		return this.NoteSound;
	}
	
	public boolean getShake(){
		return this.NoteShake;								//获取是否震动提醒
	}
	
}























