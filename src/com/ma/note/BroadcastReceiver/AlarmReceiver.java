package com.ma.note.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if("android.alarm.demo.action".equals(intent.getAction())){
			Log.d("test", "时间到了------");
//			Toast.makeText(, text, duration)
			return;
		}

	}

}
