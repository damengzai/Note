package com.ma.note;

import com.ma.note.db.NoteDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DealActivity extends Activity implements OnClickListener {
	
	private Button delete, other, cancle;
	private TextView showtv;
	private Intent intent;
	private NoteDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal);
		
		delete = (Button) findViewById(R.id.delete_btn);
		other = (Button) findViewById(R.id.other_btn);
		cancle = (Button) findViewById(R.id.cancle_btn);
		showtv = (TextView) findViewById(R.id.show_tv);
		delete.setOnClickListener(this);
		other.setOnClickListener(this);
		cancle.setOnClickListener(this);
		dao = new NoteDao(this);
		
		intent = getIntent();
		showtv.setText("确认删除\""+intent.getExtras().getString("content")+"\"这条笔记?");
	}
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		
		final View view = getWindow().getDecorView();
		final WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
		
		lp.width = 500;
		lp.height = 800;
		
		Log.d("test",(getWindow().getAttributes().height)+"-");
		getWindowManager().updateViewLayout(view, lp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent backIntent = new Intent();
		backIntent.setClass(DealActivity.this, Note.class);
		switch (v.getId()) {
		case R.id.cancle_btn:
			
			break;
		case R.id.delete_btn:
			com.ma.note.po.Note deleteNote = new com.ma.note.po.Note();
			deleteNote.setId(intent.getExtras().getInt("id"));
			dao.deleteItem(deleteNote);
		
			break;
		case R.id.other_btn:
			Toast.makeText(this, "other", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		startActivity(backIntent);
		overridePendingTransition(R.anim.startin, R.anim.startout);
		finish();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent backIntent = new Intent();
		backIntent.setClass(DealActivity.this, Note.class);
		startActivity(backIntent);
		overridePendingTransition(R.anim.startin, R.anim.startout);
		finish();
//		super.onBackPressed();
	}
}
