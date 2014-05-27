package com.example.alarm;
import java.text.DateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.util.Log;

@SuppressLint("NewApi") public class AlarmNotificationReceiver extends BroadcastReceiver {
	private static final int MY_NOTIFICATION_ID = 1;
	private static final String TAG = "AlarmNotificationReceiver";
	
	private final CharSequence tickerText ="Are You Playing Angry Birds Again!";
	private final CharSequence contentTitle  = "A Kind Reminder";
	private final CharSequence contentText = "Get back to studying!!";
	
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;
	
	private Uri soundUri = Uri.parse("android.resource://com.example.alarm./alarm"+R.raw.alarm_rooster);
	private long[] mVibratePattern ={0, 200, 200, 300};

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mNotificationIntent = new Intent(context,MainActivity.class);
		mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification.Builder notificationBuilder = new Notification.Builder(context).setTicker(tickerText).setSmallIcon(android.R.drawable.stat_sys_warning).setAutoCancel(true).setContentTitle(contentTitle).setContentText(contentText).setContentIntent(mContentIntent).setSound(soundUri).setVibrate(mVibratePattern);
		
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(MY_NOTIFICATION_ID,
				notificationBuilder.build());
		Log.i(TAG,"Sending notification at:" + DateFormat.getDateTimeInstance().format(new Date()));
	}

}
