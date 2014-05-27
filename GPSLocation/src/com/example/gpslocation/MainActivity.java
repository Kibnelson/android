package com.example.gpslocation;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings;
import android.location.LocationListener;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {
	private TextView latitude;
	private TextView longitude;
	private TextView choice;
	private CheckBox fineAcc;
	private Button choose;
	private TextView provText;
	
	private LocationManager locationManager;
	private String provider;
	private MyLocationListener mylistener;
	private Criteria criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitude = (TextView) findViewById(R.id.lat);
        longitude = (TextView) findViewById(R.id.lon);
        provText = (TextView) findViewById(R.id.prov);
        choice = (TextView) findViewById(R.id.choice);
        fineAcc = (CheckBox) findViewById(R.id.fineAccuracy);
       	choose = (Button) findViewById(R.id.chooseRadio);
       	
       	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       	criteria = new Criteria();
       	criteria.setAccuracy(Criteria.ACCURACY_COARSE);
       	choose.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(fineAcc.isChecked()){
					criteria.setAccuracy(Criteria.ACCURACY_FINE);
					 choice.setText("fine accuracy selected");
				}else{
					 criteria.setAccuracy(Criteria.ACCURACY_COARSE);
					 choice.setText("coarse accuracy selected");
				}
			}
       		
       	});
       	
       	criteria.setCostAllowed(false);
       	provider = locationManager.getBestProvider(criteria, false);
       	Location location = locationManager.getLastKnownLocation(provider);
       	mylistener = new MyLocationListener();
       	if(location != null){
       	 mylistener.onLocationChanged(location);
       	}else{
       		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       		startActivity(intent);
       	}
       	
       	locationManager.requestLocationUpdates(provider, 200, 1, mylistener);

    }
    
    private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude.setText("Lcatitude: " + String.valueOf(location.getLatitude()));
			longitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
			provText.setText(provider + " provider has been selected.");
			Toast.makeText(MainActivity.this,  "Location changed!",
					                        Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, provider + "'s status changed to "+status +"!",
						                        Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "Provider " + provider + " enabled!",
					             Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "Provider " + provider + " disabled!",
					         Toast.LENGTH_SHORT).show();
		}
    	
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
