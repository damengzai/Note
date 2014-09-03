package com.ma.note.db;

import java.util.ArrayList;
import java.util.List;

import com.ma.note.po.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteDao {
	
	private DBHelper dbhelper = null;
	
	public NoteDao(Context context) {
		// TODO Auto-generated constructor stub
		dbhelper = new DBHelper(context);
	}
	
	/**
	 * 新建笔记
	 * 
	 * **/
	public void insert(Note note){
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("noteContext", note.getNoteContext());
		values.put("iu",note.getIu());
		
		db.insert("note", null, values);
		db.close();
	}
	
	/**
	 * 查询所有笔记
	 * 
	 * **/
	public List<com.ma.note.po.Note> getAll(String iu){
		List<com.ma.note.po.Note> notes = new ArrayList<com.ma.note.po.Note>();
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		//Cursor cursor = db.query("note", new String[]{"id", "noteContext", "iu"}, "iu", new String[] {iu}, null, null, "id");
		//db.r
		//Cursor cursor = db.query("note", null, "iu", new String[]{iu}, null, null, "id");
		String queSql = "select * from note where iu = ?";
		String[] ius = {iu};
		Cursor cursor = db.rawQuery(queSql, ius);
		
		while(cursor.moveToNext()){
			Note note = new Note();
			note.setId(cursor.getInt(cursor.getColumnIndex("id")));
			note.setNoteContext(cursor.getString(cursor.getColumnIndex("noteContext")));
			
			notes.add(note);
		}
		cursor.close();
		db.close();
		
		return notes;
	}
	
	/**
	 * 删除数据
	 * 
	 * 
	 * **/
	public void deleteItem(com.ma.note.po.Note note){
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String delSql = "delete from note where id = ?";
		Object[] id = {note.getId()};
		db.execSQL(delSql, id);
		//db.delete("note", "id", new String[]{note.getId()+""});
		db.close();
		
		//db.delete("note", "id", note.getId());
		
	}

}
