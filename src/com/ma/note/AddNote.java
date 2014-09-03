package com.ma.note;

import com.ma.note.db.NoteDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNote extends Activity implements OnClickListener {
	
	private EditText context_et;
	private Button commit_btn;
	private NoteDao dao;
	private Intent getNoteIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);
		
		context_et = (EditText) findViewById(R.id.noteContext);
		commit_btn = (Button) findViewById(R.id.commit_btn);
		dao = new NoteDao(this);
		
		getNoteIntent = getIntent();
		
		commit_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int iu = getNoteIntent.getExtras().getInt("iu", 0);
		switch (v.getId()) {
		case R.id.commit_btn:
			commit(iu);
			Intent intent = new Intent();
			intent.setClass(AddNote.this, Note.class);
			this.startActivity(intent);
			finish();
			overridePendingTransition(R.anim.startin, R.anim.startout);
			break;

		default:
			break;
		}
	}

	private void commit(int iu) {
		String context = context_et.getText().toString().trim();
		com.ma.note.po.Note note = new com.ma.note.po.Note();
		note.setNoteContext(context);
		note.setIu(iu);
		dao.insert(note);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AddNote.this, Note.class);
		this.startActivity(intent);
		finish();
		overridePendingTransition(R.anim.startin, R.anim.startout);
//		super.onBackPressed();
	}
}
