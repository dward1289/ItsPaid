package com.DevonaWard.itspaid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;




public class SavedBills extends Activity {

	//ListView
	ListView listView;
	//Saved files
	 String[] SavedFiles;
	 String ret;
	 int selected;
	 View theView;
	 ArrayAdapter<String> adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_bills);
		 
		listView = (ListView) findViewById(R.id.list);

		SavedFiles = getApplicationContext().fileList();
		
		if(SavedFiles.length == 0){
			new AlertDialog.Builder(this)
		    .setTitle("It's Paid")
		    .setMessage("Saved Bills list is empty. Tap the plus button to add a bill to the list.")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		    		
		        }
		     })
		     .show();
		}

		ShowSavedFiles();
			
        //ListView onClick
        listView.setOnItemClickListener(new OnItemClickListener()
           {
                   public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                   {
                	   selected = position;
                	   v.setBackgroundColor(Color.GRAY);
                	   theView = v;
                	   readFromFile();
                        }
           });
	}

	//Populate list with saved bills
	  void ShowSavedFiles(){
		    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,SavedFiles);
		   listView.setAdapter(adapter);
		  }
	  
	  //Read data stored.
	  private String readFromFile() {
		    try {
		        InputStream inputStream = openFileInput(SavedFiles[selected]);

		        if ( inputStream != null ) {
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String receiveString;
		            StringBuilder stringBuilder = new StringBuilder();

		            while ((receiveString = bufferedReader.readLine()) != null ) {
		            	if(receiveString.length() != 0){
		                stringBuilder.append(receiveString+"\n");
		            }
		            	
		            }
		            inputStream.close();
		            ret = stringBuilder.toString();
		        }
		    }
		    catch (FileNotFoundException e) {
		        Log.e("login activity", "File not found: " + e.toString());
		    } catch (IOException e) {
		        Log.e("login activity", "Can not read file: " + e.toString());
		    }
		    Log.i("FILE", ret);
		    
		    //Display saved bill data
		    new AlertDialog.Builder(this)
		    .setTitle("Bill Info")
		    .setMessage(ret)
		    .setPositiveButton("It's Paid!", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            theView.setBackgroundColor(Color.rgb(0, 102, 51));
		            
		        }
		     })
		    .setNeutralButton("Will Be Paid", new DialogInterface.OnClickListener() {
		        @SuppressLint("SimpleDateFormat")
				public void onClick(DialogInterface dialog, int which) { 
		        	theView.setBackgroundColor(Color.RED);	  
		        	
		        	
		        	
		        	
		        }
		        
		     })
		     .setNegativeButton("Delete Bill", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	deleteFile(SavedFiles[selected]);
		        	finish();
		        	startActivity(getIntent());
		        }
		      })
		     .show();
		    return ret; 
		}
		
	  
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.addBills:
		        	openAdd();
		            return true;
		        case R.id.aboutIt:
		            openAbout();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

		public void openAdd(){
			Intent mainIntent = new Intent(this, MainPage.class);
			startActivity(mainIntent);
		}
		
		public void openAbout(){
			Intent aboutIntent = new Intent(this, AboutPage.class);
			startActivity(aboutIntent);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}

