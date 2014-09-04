package com.ma.note.adapter;

import java.util.List;

import com.ma.note.DealActivity;
import com.ma.note.Note;
import com.ma.note.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter{
	
	private Activity context;
	private List<com.ma.note.po.Note> notes;
	
	
	public NoteAdapter(Activity context, List<com.ma.note.po.Note> notes){
		this.context = context;
		this.notes = notes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.noteitem, null);
			viewHolder = new ViewHolder();
			viewHolder.noteContext = (TextView) convertView.findViewById(R.id.note_item);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final com.ma.note.po.Note note = notes.get(position);
		viewHolder.noteContext.setText(note.getNoteContext());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(context, DealActivity.class);
				intent.putExtra("content", note.getNoteContext());
				intent.putExtra("id", note.getId());
				context.startActivity(intent);
				context.overridePendingTransition(R.anim.startin, R.anim.startout);
				context.finish();
//				overridePendingTransition(R.anim.startin, R.anim.startout);
				
//				AlertDialog.Builder builder = new Builder(context);
//				builder.setTitle("确认删除？");
//				builder.setPositiveButton("删除", new DialogInterface.OnClickListener(){
//					NoteDao dao = new NoteDao(context);
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						dao.deleteItem(note);
//						notifyObserver();
//					}
//					
//				});
//				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						
//					}
//				});
//				builder.create();
//				builder.show();
				//dao.deleteItem(note);
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView noteContext;
	}
	
	public void notifyObserver() {
		// TODO Auto-generated method stub
		Note note = new Note();
		//NoteDao dao = new NoteDao();
		//notes = dao.getAll();
		note.update();
	}

}
