package com.example.alarm;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent,mLoggerReceiverIntent;
	private PendingIntent mNotificationReceiverPendingIntent,mLoggerReceiverPendingIntent;
	private static final long INITIAL_ALARM_DELAY = 2*600*1000L;
	private static final long JITTER  = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        mNotificationReceiverIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, mNotificationReceiverIntent, 0);
        
        mLoggerReceiverIntent = new Intent(MainActivity.this,AlarmLoggerReceiver.class);
        mLoggerReceiverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, mLoggerReceiverIntent, 0);
        
        final Button singleAlarmButton = (Button) findViewById(R.id.single_alarm_button);
        
        singleAlarmButton.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+INITIAL_ALARM_DELAY, mNotificationReceiverPendingIntent);
				mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+INITIAL_ALARM_DELAY, mLoggerReceiverPendingIntent);
				Toast.makeText(getApplicationContext(), "Single Alarm Set",Toast.LENGTH_LONG).show();
			}
        });
        
        final Button repeatingAlarmButton = (Button)findViewById(R.id.repeating_alarm_button);
        repeatingAlarmButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY, AlarmManager.INTERVAL_FIFTEEN_MINUTES, mNotificationReceiverPendingIntent);
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY, AlarmManager.INTERVAL_FIFTEEN_MINUTES+JITTER, mLoggerReceiverPendingIntent);
				Toast.makeText(getApplicationContext(), "Repeating Alarm Set",
						Toast.LENGTH_LONG).show();
			}
        	
        
        });
        
        final Button inexactRepeatingAlarmButton  = (Button)findViewById(R.id.inexact_repeating_alarm_button);
        inexactRepeatingAlarmButton .setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 
						SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY,
						AlarmManager.INTERVAL_FIFTEEN_MINUTES, mNotificationReceiverPendingIntent);
				mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 
						SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY+JITTER, 
						AlarmManager.INTERVAL_FIFTEEN_MINUTES, mLoggerReceiverPendingIntent);
				Toast.makeText(getApplicationContext(),
						"Inexact Repeating Alarm Set", Toast.LENGTH_LONG)
						.show();
			}
        });
        
        final Button cancelRepeatingAlarmButton = (Button)findViewById(R.id.cancel_repeating_alarm_button);
        cancelRepeatingAlarmButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlarmManager.cancel(mNotificationReceiverPendingIntent);
				mAlarmManager.cancel(mLoggerReceiverPendingIntent);
				Toast.makeText(getApplicationContext(), "Repeating Alarms Cancelled", Toast.LENGTH_LONG).show();
			}
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
