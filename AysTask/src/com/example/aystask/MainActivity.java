package com.example.aystask;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.view.View.OnClickListener;
public class MainActivity extends Activity {
	final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
    }
    
    private class LoadWebPageASYNC extends AsyncTask<String, Void,String>{

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			WebView webView = (WebView) findViewById(R.id.webView);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(urls[0]);
			return null;
		}
		protected void onPostExecute(String result) {
		}
    	
    }
    public void dummyFunc(View view){
    	Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
    		 
    }
    		 
    public void readWebpage(View view) {
        LoadWebPageASYNC task = new LoadWebPageASYNC();
        task.execute(new String[] { "http://www.javacodegeeks.com" });
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
