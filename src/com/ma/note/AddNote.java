package com.ma.note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ma.note.db.NoteDao;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddNote extends Activity implements OnClickListener {

	private EditText context_et;
	private Button commit_btn;
	private LinearLayout alarmLayout, timeLayout;
	private ImageView checkimage;
	private TextView dateTv, timeTv, showTv;
	private NoteDao dao;
	private Intent getNoteIntent;
	private Calendar calendar;
	private long millionSeconds;
	private int yyyy, MM, dd, hh, mm;
	private boolean visiable = false;
	private long millin = 0l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);
		calendar = Calendar.getInstance();

		context_et = (EditText) findViewById(R.id.noteContext);
		commit_btn = (Button) findViewById(R.id.commit_btn);
		alarmLayout = (LinearLayout) findViewById(R.id.add_alarm_layout);
		checkimage = (ImageView) findViewById(R.id.check_alarm_or_not);
		dateTv = (TextView) findViewById(R.id.date_tv);
		timeTv = (TextView) findViewById(R.id.time_tv);
		timeLayout = (LinearLayout) findViewById(R.id.time_layout);
		showTv = (TextView) findViewById(R.id.show_time);

		dao = new NoteDao(this);
		getNoteIntent = getIntent();

		commit_btn.setOnClickListener(this);
		alarmLayout.setOnClickListener(this);
		dateTv.setOnClickListener(this);
		timeTv.setOnClickListener(this);
		timeLayout.setOnClickListener(this);
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
		case R.id.add_alarm_layout:

			visiable = !visiable;
			if (visiable) {
				checkimage.setImageResource(R.drawable.check_in);
				timeLayout.setVisibility(LinearLayout.VISIBLE);
			} else {
				checkimage.setImageResource(R.drawable.check_out);
				timeLayout.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.date_tv:
			showDateDialog();
			break;

		case R.id.time_tv:
			showTimeDialog();
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
		// super.onBackPressed();
	}

	// 添加闹钟
	private void setAlarmTime(Context context, long timeInMillis) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("android.alarm.demo.action");
		// Intent intent = new Intent(AddNote.this, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, timeInMillis, sender);
		// int interval = 365 * 24 * 60 * 60 * 1000;// 时间间隔
		// am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval,
		// sender);
	}

	private void showDateDialog() {

		DatePickerDialog dateDialog = new DatePickerDialog(AddNote.this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						yyyy = year;
						MM = monthOfYear;
						dd = dayOfMonth;
						showTv.setText(yyyy + "年" + (MM + 1) + "月" + dd + "日");
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dateDialog.show();
		
	}

	private void showTimeDialog() {
		TimePickerDialog timeDialog = new TimePickerDialog(AddNote.this,
				new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						hh = hourOfDay;
						mm = minute;
						// showTv.add
						millin = formatTime(yyyy, MM + 1, dd, hh, mm);
						setAlarmTime(AddNote.this, millin);
						if(hh != 0){
							Log.d("test", "hhhh");
							showTv.setText(showTv.getText().toString()+" " + hh + " : " + mm);
						}
						if(mm != 0){
							Log.d("test", "mmmm");
						}
						
						Toast.makeText(AddNote.this, "闹铃添加成功！",
								Toast.LENGTH_LONG).show();
						
					}
				}, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true);
		timeDialog.show();

	}

	private long formatTime(int yyyy, int MM, int dd, int hh, int mm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(yyyy);
		if (MM < 10) {
			sbuffer.append("0" + MM);
		} else {
			sbuffer.append(MM);
		}
		if (dd < 10) {
			sbuffer.append("0" + dd);
		} else {
			sbuffer.append(dd);
		}
		if (hh < 10) {
			sbuffer.append("0" + hh);
		} else {
			sbuffer.append(hh);
		}
		if (mm < 10) {
			sbuffer.append("0" + mm);
		} else {
			sbuffer.append(mm);
		}
		Log.d("test", sbuffer.toString());
		try {
			millionSeconds = sdf.parse(sbuffer.toString()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return millionSeconds;
	}

}
