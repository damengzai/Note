package com.ma.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static int currentVersion = 1;

	
	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		this(context, currentVersion);
		
	}
	public DBHelper(Context context, int version){
		super(context, "note.db", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		String creatSql = "create table note(id integer primary key autoincrement , noteContext varchar(1000) not null, iu varchar(10) not null)";
		db.execSQL(creatSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
