package com.example.loaderexample;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.ContentProviderOperation;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class LoadActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{
	private TextView resTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        resTextView = (TextView) findViewById(R.id.res);
        
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI).withValue(RawContacts.ACCOUNT_TYPE,"Test").withValue(RawContacts.ACCOUNT_NAME, "CTE").build());
        ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex) .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE).withValue(StructuredName.DISPLAY_NAME, "test name").withValue(Phone.NUMBER, 1234567890).build());
        try {
            //Use below statement to insert a new contact
            //getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
        getSupportLoaderManager().initLoader(1, null, (LoaderCallbacks<Bundle>) this);
       
    }
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
     Uri CONTENT_URI = ContactsContract.RawContacts.CONTENT_URI;
     return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
     cursor.moveToFirst();
     StringBuilder res = new StringBuilder();
     while (!cursor.isAfterLast()) {
      res.append("\n" + cursor.getString(21) + "-" + cursor.getString(22));

      cursor.moveToNext();
     }
     resTextView.setText(res);

    }

    public void onLoaderReset(Loader<Cursor> loader) {
     // If the Cursor is being placed in a CursorAdapter, you should use the
     // swapCursor(null) method to remove any references it has to the
     // Loader's data.
    }

    @Override
    protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.load, menu);
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
