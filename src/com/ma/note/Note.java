package com.ma.note;

import java.util.List;

import com.ma.note.adapter.NoteAdapter;
import com.ma.note.db.NoteDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class Note extends Activity implements OnClickListener {

	private ImageButton add_btn, add_btn1, add_btn2, add_btn3;
	private NoteDao dao;
	private List<com.ma.note.po.Note> notes, notes1, notes2, notes3;
	private ListView impnoturge;// 重要但不紧急
	private ListView impurge;// 重要且紧急
	private ListView noimpnoturge;// 不重要且不紧急
	private ListView notimpurge;// 不重要但紧急

	private NoteAdapter noteAdapter, noteAdapter1, noteAdapter2, noteAdapter3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);

		add_btn = (ImageButton) findViewById(R.id.add_btn);
		add_btn1 = (ImageButton) findViewById(R.id.add_btn1);
		add_btn2 = (ImageButton) findViewById(R.id.add_btn2);
		add_btn3 = (ImageButton) findViewById(R.id.add_btn3);

		impnoturge = (ListView) findViewById(R.id.note_list);
		impurge = (ListView) findViewById(R.id.note_list1);
		noimpnoturge = (ListView) findViewById(R.id.note_list2);
		notimpurge = (ListView) findViewById(R.id.note_list3);

		update();

		add_btn.setOnClickListener(this);
		add_btn1.setOnClickListener(this);
		add_btn2.setOnClickListener(this);
		add_btn3.setOnClickListener(this);

		// dao = new NoteDao(this);
		// notes = dao.getAll();

		// noteAdapter = new NoteAdapter(this, notes);
		// noteAdapter.notifyDataSetChanged();
		// noteList.setAdapter(noteAdapter);
		impnoturge.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// noteAdapter.notifyDataSetChanged();
		update();
		if (noteAdapter != null) {
			noteAdapter.notifyDataSetChanged();
		}

		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.add_btn: // 重要但不紧急

			intent.putExtra("iu", 0);

			break;
		case R.id.add_btn1: // 重要且紧急
			intent.putExtra("iu", 1);
			break;
		case R.id.add_btn2: // 不重要且不紧急
			intent.putExtra("iu", 2);
			break;

		case R.id.add_btn3: // 不重要但紧急
			intent.putExtra("iu", 3);
			break;

		default:
			break;

		}
		intent.setClass(Note.this, AddNote.class);

		this.startActivity(intent);
		finish();
		overridePendingTransition(R.anim.startin, R.anim.startout);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	public void update() {
		// TODO Auto-generated method stub
		dao = new NoteDao(this);
		notes = dao.getAll(0 + "");
		notes1 = dao.getAll(1 + "");
		notes2 = dao.getAll(2 + "");
		notes3 = dao.getAll(3 + "");

		if (notes.size() > 0) {
			noteAdapter = new NoteAdapter(this, notes);
			noteAdapter.notifyDataSetChanged();
			impnoturge.setAdapter(noteAdapter);
//			impurge.setAdapter(noteAdapter);
//			noimpnoturge.setAdapter(noteAdapter);
//			notimpurge.setAdapter(noteAdapter);
		}
		if (notes1.size() > 0) {
			noteAdapter1 = new NoteAdapter(this, notes1);
			noteAdapter1.notifyDataSetChanged();
			impurge.setAdapter(noteAdapter1);
		}
		if (notes2.size() > 0) {
			noteAdapter2 = new NoteAdapter(this, notes2);
			noteAdapter2.notifyDataSetChanged();

			noimpnoturge.setAdapter(noteAdapter2);
		}
		if (notes3.size() > 0) {
			noteAdapter3 = new NoteAdapter(this, notes3);
			noteAdapter3.notifyDataSetChanged();

			notimpurge.setAdapter(noteAdapter3);
		}

	}
}
