package android_2.lesson02.app04;

import android.app.ListActivity;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android_2.lesson02.app04.R;
import android_2.lesson02.app04.DBEmpl.TableDep;

public class ActMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		/* Invoke a parent method */
		super.onCreate(savedInstanceState);
		
		/* Load user interface */
		setContentView(R.layout.act_main);
		
		/* Initialize UI components */
		ListView lvList = (ListView) findViewById(R.id.lvList);
		Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG);
		/* Get cursor from content provider by content resolver! */
		Cursor c = getContentResolver().query(DBEmplProvider.CONTENT_URI, null, 
				null, null, null);		
		
		/* Create arrays of columns and UI elements */
		String[] from = {DBEmpl.TableDep.C_NAME, DBEmpl.TableDep.C_LOCA};
		int[] to = {R.id.tvName, R.id.tvLocation};
		
		/* Create simple Cursor adapter */
		SimpleCursorAdapter lvAdapter = new SimpleCursorAdapter(this, 
				R.layout.list_item, c, from, to, 1);
		
		/* setting up adapter to list view  */
		lvList.setAdapter(lvAdapter);
		lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
				Uri uri = ContentUris.withAppendedId(DBEmplProvider.CONTENT_URI_DEP_ITEM, i);
				getContentResolver().delete(uri, null, null);

			}
		});

		lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getApplicationContext(),"long clicked",Toast.LENGTH_SHORT).show();

				Uri uri = ContentUris.withAppendedId(DBEmplProvider.CONTENT_URI_DEP_ITEM, i);
				ContentValues cv = new ContentValues();
				cv.put(TableDep.C_NAME, "updated Department ");
				cv.put(TableDep.C_LOCA, "updated Location ");

				ContentValues icv = new ContentValues();
				icv.put(TableDep.C_NAME, "inserted Department ");
				icv.put(TableDep.C_LOCA, "inserted Location ");

				//getContentResolver().insert(DBEmplProvider.CONTENT_URI, icv);

				getContentResolver().update(DBEmplProvider.CONTENT_URI, cv, null, null);

				return false;
			}
		});



//		lvList.setOnItemClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				getContentResolver().delete(DBEmplProvider.CONTENT_URI, null, null);
//
//				Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG);
//			}
//		});
//
		/* 
		 * Insert new value 
		 * */
		
		/* Create a new map of values, where column names are the keys */
		ContentValues v = new ContentValues();
		
		/* Fill values */
		v.put(TableDep.C_NAME, "Department 01");
		v.put(TableDep.C_LOCA, "Location 01");
		
		/* Insert value to a DB */
		//getContentResolver().insert(DBEmplProvider.CONTENT_URI,v);
				
	}


//	public void onClick(View view) {
////		Cursor c = getContentResolver().query(DBEmplProvider.CONTENT_URI_DEP_ITEM, null,
////				null, null, null);
//
//		getContentResolver().delete(DBEmplProvider.CONTENT_URI, null, null);
//
//		Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG);
//	}
}
